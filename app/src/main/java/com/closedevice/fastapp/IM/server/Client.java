package com.closedevice.fastapp.IM.server;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.closedevice.fastapp.IM.models.Data;
import com.closedevice.fastapp.IM.models.Ping;
import com.closedevice.fastapp.IM.models.SelectGuide;
import com.closedevice.fastapp.IM.protocol.AudioProtocol;
import com.closedevice.fastapp.IM.protocol.BasicProtocol;
import com.closedevice.fastapp.IM.protocol.DataProtocol;
import com.closedevice.fastapp.IM.util.ChatPacket;
import com.closedevice.fastapp.IM.util.GetTime;
import com.closedevice.fastapp.IM.util.ProtocolType;
import com.closedevice.fastapp.IM.util.SocketUtil;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

import px.com.photoselectorlibrary.ImageSelectorListActivity;

import static com.closedevice.fastapp.AppConstant.SERVER_IP;
import static com.closedevice.fastapp.AppConstant.SERVER_PORT;


public class Client implements Comparable {
	private static final String TAG = "Client";


	private ThreadConnHandler threadConnHandler;
	private ThreadReceiveHandler threadReceiveHandler;
	private ThreadSendHander threadSendHander;
	
	private Socket socket;
	private Integer clientID; 

	
	private volatile ConcurrentLinkedQueue<BasicProtocol> queueToSend = new ConcurrentLinkedQueue<>();

	private String userHostAddress = null;

	private ClientState curState = ClientState.Uninitialized;

	private enum ClientState {
		Uninitialized, End, Teacher_Begin, Tea_Responding, Stu_Begin, Stu_Listening
	}

	public Client() {
		threadConnHandler = new ThreadConnHandler();
		threadConnHandler.start();
	}

	public void onConnect() {

		try {

			// 开启接收线程
			threadReceiveHandler = new ThreadReceiveHandler();
			threadReceiveHandler.inputStream = new DataInputStream(socket.getInputStream());
			threadReceiveHandler.start();

			// 开启发送线程
			threadSendHander = new ThreadSendHander();
			threadSendHander.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnect()
	{
		//TODO
	}
	
	public void stop() {
		if (threadReceiveHandler != null) {
			threadReceiveHandler.isCancel = true;
			threadReceiveHandler.interrupt();
			if (threadReceiveHandler.inputStream != null) {
				SocketUtil.closeInputStream(threadReceiveHandler.inputStream);
				threadReceiveHandler.inputStream = null;
			}
			threadReceiveHandler = null;
		}

		if (threadSendHander != null) {
			threadSendHander.isCancel = true;
			threadSendHander.interrupt();
			if (threadSendHander.outputStream != null) {
				synchronized (threadSendHander.outputStream) {// 防止写数据时停止，写完再停
					threadSendHander.outputStream = null;
				}
			}
			threadSendHander = null;
		}
	}

	

	// 添加发送信息返回给自身
	public void sendMessage(BasicProtocol selfData) {
		if (!isConnected()) {
			Log.d(TAG,"没有连接");
			return;
		}

		//Log.d(TAG,"send Msg to Client "+getClientID());
		queueToSend.offer(selfData);
		toNotifyAll(queueToSend);// 有新增待发送数据，则唤醒发送线程
	}

	private void toWaitAll(Object o) {
		synchronized (o) {
			try {
				o.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void toNotifyAll(Object obj) {
		synchronized (obj) {
			obj.notifyAll();
		}
	}

 

	/**
	 * 是否在线
	 * 
	 * @return
	 */
	public boolean isConnected() {
		if (socket.isClosed() || !socket.isConnected()) {
			return false;
		}
		return true;
	}

	public class ThreadReceiveHandler extends Thread {

		private DataInputStream inputStream;
		private boolean isCancel;

		@Override
		public void run() {
			while (!isCancel) {
				if (!isConnected()) {
					isCancel = true;
					break;
				}
				try {
					if (inputStream != null) {
						ChatPacket cp = SocketUtil.readFromStream(Client.this, inputStream);
						
						Log.d(TAG,"====================== Begining of a MSG coming from Client: "+clientID);
						Log.d(TAG,"+++["+ GetTime.getTimeShort()+"][BEGIN] Old State = " + curState);
						Log.d(TAG,"Protocol num  " + cp.protocolNum);
						Log.d(TAG,"packet size =   " + cp.size);
						
						if (cp.protocolNum != ProtocolType.AudioProtocol.getType())
						{
							Log.d(TAG,"coming content in text:" + cp.turnToContent);
							//Log.d(TAG,"coming content in byte[]:" + Arrays.toString(cp.data));
						}
						
						if (cp.protocolNum == ProtocolType.Ping.getType())
						{
							//to do
							Log.d(TAG,"Received a ping");
						}else{
							
							switch (curState) {
							case Uninitialized:
								ackWrongMsg("during uninitialized");
								break;
	
								case Teacher_Begin:
								if (cp.protocolNum == ProtocolType.GROUP_CREATE_AND_JOIN.getType()) {
									parseTeaBegin(cp);
									curState = ClientState.Tea_Responding;
								} else {
									ackWrongMsg("during teacher_afterlogin");
								}
								break;
	
							case Tea_Responding:
								if (cp.protocolNum == ProtocolType.Data.getType()) {
									parseData(cp);
								} else if (cp.protocolNum == ProtocolType.GROUP_DISMISS.getType()) {
									removeTeacher(cp);
									curState = ClientState.End;
								} else if (cp.protocolNum == ProtocolType.AudioProtocol.getType()) {
									parseAudio(cp);
								} else if (cp.protocolNum == ProtocolType.GROUP_CREATE_AND_JOIN.getType()){
									parseTeaBegin(cp);
								} else	{	
									ackWrongMsg("during guide_guiding");
								}
								break;
								
								case Stu_Begin:
//								if (cp.protocolNum == ProtocolType.GuideList.getType()) {
//								} else if (cp.protocolNum == ProtocolType.SelectGuide.getType()) {
//									parseSelectGuide(cp);
//									curState = ClientState.Tst_Listening;
//								} else {
									ackWrongMsg("during Stu_AfterLogin");
								break;
	
							case Stu_Listening:
//								if (cp.protocolNum == ProtocolType.GuideList.getType()) {
//								} else if (cp.protocolNum == ProtocolType.SelectGuide.getType()) {
//									parseSelectGuide(cp);
//								} else
								if (cp.protocolNum == ProtocolType.Data.getType()){
									parseData(cp);
								} else if(cp.protocolNum == ProtocolType.AudioProtocol.getType()){
									parseAudio(cp);
								}else if (cp.protocolNum == ProtocolType.GROUP_LEAVE.getType()) {
									removeStu(cp);
									curState = ClientState.Stu_Begin;
								} else if (cp.protocolNum == ProtocolType.Data.getType()){
									parseData(cp);
								} else {
									ackWrongMsg("during Stu_Listening");
								}
								break;
							case End:
								break;
							}
	
							Log.d(TAG,"+++["+GetTime.getTimeShort()+"][END], New State = " + curState);
						}

					}
				} catch (EOFException e) {
					Log.d(TAG,"客户端 sends EOF. Socket is closed");
					SocketUtil.closeInputStream(inputStream);
					ClientHub.onClientDisconnect(Client.this);
					break;
				} catch (IOException e) {
					e.printStackTrace();
					ClientHub.onClientDisconnect(Client.this);
					break;
				}

			}
			SocketUtil.closeInputStream(inputStream);
		}



	}

	public void ackWrongMsg(String text) {
		Log.d(TAG,"%%%%%%%%%% ackWrongMsg (TODO)" + text);
	}


	public void parseTeaBegin(ChatPacket cp) {

		Log.d(TAG,"[Command] TeacherBegin");
		//call ClientHub
		//this operation always can be successful;
		ClientHub.onGuideBegin(Client.this);


		DataProtocol protocol = new DataProtocol();
		protocol.setData(cp.turnToContent);
		protocol.setProtocolNum(ProtocolType.GROUP_CREATE_AND_JOIN.getType());
		Log.d(TAG,"协议号：" + protocol.getProtocolNum());

		sendMessage(protocol);

	}



//		private void parseSelectGuide(ChatPacket cp) {
//
//			Log.d(TAG,"[Command] SelectGuide");
//
//			Gson gson = new Gson();
//			SelectGuide sel = gson.fromJson(cp.turnToContent, SelectGuide.class);
//			Log.d(TAG,"服务器接收到的导游ID为：" + sel.getGuideid());
//
//			// 根据选择的导游加到该导游对应的群组
//			ClientHub.onSelectGuide(Client.this, sel.getGuideid());
//
//			SelectGuide sg = new SelectGuide();
//			sg.setStatus("请求成功");
//			sg.setGroupid(sel.getGuideid());
//			String temp = gson.toJson(sg);
//
//			DataProtocol protocol = new DataProtocol();
//			protocol.setProtocolNum(ProtocolType.SelectGuide.getType());
//			protocol.setData(temp);
//
//			sendMessage(protocol);
//
//		}

	public void parseData(ChatPacket cp) {
		Gson gson = new Gson();
		Data data = gson.fromJson(cp.turnToContent, Data.class);

		Log.d(TAG,"Client(ID="+getClientID()+"：" + data.toString());

		String temp = data.getData();
//		DataProtocol protocol = new DataProtocol();
//		protocol.setData(cp.turnToContent);
//		protocol.setProtocolNum(ProtocolType.Data.getType());

		//call ClientHub
		//ClientHub.sendMsgToGroupExceptSelf(Client.this, protocol);

	}

	public void removeTeacher(ChatPacket cp) throws IOException {
			/*
			 * TODO
			 * 1. close group
			 * 2. ask all group member to stop
			 */

		Log.d(TAG,"[Command] removeTeacher");

		Map<String, Boolean> backdate = new HashMap<String, Boolean>();
		backdate.put("status", true);
		Gson gson = new Gson();
		String temp = gson.toJson(backdate);
		//JSONObject obj = JSONObject.fromObject(backdate);

		DataProtocol protocol = new DataProtocol();
		protocol.setProtocolNum(ProtocolType.GROUP_DISMISS.getType());

		Data data = new Data();
		data.setPattion(3);
		data.setData(temp);

		String temp2 = gson.toJson(data);
		protocol.setData(temp2);

		ClientHub.sendMsgToClient(Client.this, protocol);


		DataProtocol toTourist = new DataProtocol();
		toTourist.setProtocolNum(ProtocolType.GROUP_LEAVE.getType());
		Data data2 = new Data();
		data2.setPattion(3);
		data2.setData(temp);

		String temp3 = gson.toJson(data2);

		toTourist.setData(temp3);

		ClientHub.sendMsgToGroupExceptSelf(Client.this,toTourist);

	}

	public void removeStu(ChatPacket cp) throws IOException {

		Log.d(TAG,"[Command] RemoveTourist");
		Map<String, Boolean> backdate = new HashMap<String, Boolean>();
		backdate.put("status", true);
		Gson gson = new Gson();
		String temp = gson.toJson(backdate);

		DataProtocol toTourist = new DataProtocol();
		toTourist.setProtocolNum(ProtocolType.GROUP_LEAVE.getType());
		toTourist.setData(temp);

		sendMessage(toTourist);
		Log.d(TAG,"[Command] removeStu");
	}



	public void parsePing(ChatPacket cp) {
		Gson gson = new Gson();
		Ping ping = gson.fromJson(cp.turnToContent, Ping.class);
		Log.d(TAG,"心跳id+预留信息：" + ping.getPingid() + ping.getReserved());

		DataProtocol protocol = new DataProtocol();
		protocol.setData(cp.turnToContent);
		protocol.setProtocolNum(ProtocolType.Ping.getType());

		ClientHub.sendMsgToClient(Client.this, protocol);
	}

	public void parseAudio(ChatPacket cp) {

		Log.d(TAG,"[Command] ParseAudio");

		AudioProtocol protocol = new AudioProtocol();
		protocol.setAudioData(cp.data);
		protocol.setProtocolNum(ProtocolType.AudioProtocol.getType());

		//ClientHub.sendMsgToGroupExceptSelf(Client.this, protocol);

		Log.d(TAG,"当前收到声音" + cp.data.length + "字节");

	}

//	public String getUserName() {
//		if (userName == null)
//		{
//			GuideDao guideDao = new GuideDao();
//			userName = guideDao.findNameById(clientID);
//		}
//
//		return userName;
//
//	}

	public Integer getClientID() {
		return clientID;
	}
	
	public int getSizeOfQueueToSend()
	{
		return queueToSend.size();
	}

	public class ThreadSendHander extends Thread {
		private DataOutputStream outputStream;
		private boolean isCancel;

		@Override
		public void run() {
			while (!isCancel) {
				if (!isConnected()) {
					isCancel = true;
					break;
				}

				BasicProtocol selfpro = queueToSend.poll(); 

				if (selfpro == null) {
					toWaitAll(queueToSend);
				} else {
					try {
						outputStream = new DataOutputStream(socket.getOutputStream());
						synchronized (outputStream) {
							SocketUtil.writeToStream(selfpro, outputStream);
							Log.d(TAG,String.format(
									"[%s] ***服务器 sends to Client (%d): size=%d protocol=%d content=%s",
									GetTime.getTimeShort(),
									getClientID(),
									selfpro.getLength(),
									selfpro.getProtocolNum(),
									selfpro.toString()));
						}
					} catch (EOFException e1) {
						Log.d(TAG,"这是合法的，客户端已关闭");
						e1.printStackTrace();
						ClientHub.onClientDisconnect(Client.this);
						break;
					} catch (Exception e) {
						e.printStackTrace();
						ClientHub.onClientDisconnect(Client.this);
					}
				}

			}
			SocketUtil.closeOutputStream(outputStream);

		}

	}

	public class ThreadConnHandler extends Thread {
		@Override
		public void run() {
			try {
				socket = new Socket();
				socket.connect(
						new InetSocketAddress(SERVER_IP, SERVER_PORT));
				Log.d(TAG, "---------服务器连接成功");
			} catch (UnknownHostException e) {
				e.printStackTrace();
				Log.d(TAG, "---------服务器地址无法解析");
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("Network", "Socket io异常");
			}
		}
	}


	@Override
	public int compareTo(Object arg0) {
		return this.clientID - ((Client)arg0).clientID;
	}




}
