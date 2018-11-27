package com.chat.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.tribes.util.Arrays;

import com.google.gson.Gson;
import com.imsdk.bean.Data;
import com.imsdk.bean.Ping;
import com.imsdk.beanparser.BeanPacket;
import com.imsdk.beanparser.BlockBeanParser;
import com.imsdk.beanparser.StreamBeanParser;
import com.imsdk.protocol.Client;
import com.imsdk.protocol.ICmdProtocol;
import com.imsdk.util.GetTime;
import com.imsdk.util.MLog;

public class ClientProtocol implements ICmdProtocol {

	private boolean DEBUG = true;
	private String TAG = "Protocol";

	protected ClientState curState = ClientState.Uninitialized;
	private final Client client;
	// Properties properties = new Properties();
	private boolean succ = false;

	public enum ClientState {
		Uninitialized, End, Teacher_Begin, Tea_Responding, Stu_Begin, Stu_Listening
	}

	private ArrayList<ICmdProtocol> proChain = new ArrayList<ICmdProtocol>();

	public ClientProtocol(Client client) {
		this.client = client;
	}

	@Override
	public boolean onCmd(BeanPacket cp) {

		if (cp.protocolNum == ProtocolType.PING) {
			// //to do
			// MLog.i(DEBUG, TAG, );
			// MLog.i(DEBUG, TAG, "Received a ping");
			// MLog.i(DEBUG, TAG, );
			return true;
		}

		MLog.i(DEBUG, "", "");
		MLog.d(DEBUG, TAG, String.format("[Client %s] Coming a message", getClient().getClientID()));

		MLog.i(DEBUG, TAG, "+++[" + GetTime.getTimeShort() + "][BEGIN] Old State = " + curState);

		MLog.i(DEBUG, TAG,
				String.format("Protocol Type: %s (%d) ", ProtocolType.getMsgName(cp.protocolNum), cp.protocolNum));

		MLog.i(DEBUG, TAG, "Packet Size =   " + cp.size);

		if (cp.protocolNum != ProtocolType.AUDIO) {
			MLog.i(DEBUG, TAG, "coming content in text:" + cp.turnToContent);
		} else {
			MLog.i(DEBUG, TAG, "当前收到声音" + cp.data.length + "字节");
			MLog.i(DEBUG, TAG, "收到的声音字节：" + Arrays.toString(cp.data));
		}

		switch (curState) {
		case Uninitialized:
			if (cp.protocolNum == ProtocolType.GROUP_CREATE_AND_JOIN) {
				curState = ClientState.Teacher_Begin;
				succ = true;
			} else {
				break;
			}
			break;

		case Teacher_Begin:
			if (cp.protocolNum == ProtocolType.GROUP_CREATE_AND_JOIN) {
				parseGuideBegin(cp);
				curState = ClientState.Tea_Responding;
				succ = true;
				break;
			} else {
				break;
			}

		case Tea_Responding:
			switch (cp.protocolNum) {
			case ProtocolType.DATA:
				parseData(cp);
				succ = true;
				break;

			case ProtocolType.GROUP_DISMISS:
				parseGroupDismiss(cp);
				curState = ClientState.End;
				succ = true;
				break;

			case ProtocolType.AUDIO:
				parseAudio(cp);
				succ = true;
				break;

			case ProtocolType.GROUP_CREATE_AND_JOIN:
				parseGuideBegin(cp);
				succ = true;
				break;
			}

			break;

		case Stu_Begin:
			// if (cp.protocolNum == ProtocolType.GROUP_LIST) {
			// parseGuideList(cp);
			// succ = true;
			// break;
			// }
			// else if (cp.protocolNum == ProtocolType.GROUP_JOIN) {
			// parseSelectGuide(cp);
			// curState = ClientState.Stu_Listening;
			// succ = true;
			// break;
			// }
			break;

		case Stu_Listening:
			switch (cp.protocolNum) {
			// case ProtocolType.GROUP_LIST:
			// parseGuideList(cp);
			// succ = true;
			// break;

			case ProtocolType.GROUP_JOIN:
				// parseSelectGuide(cp);
				succ = true;
				break;

			case ProtocolType.GROUP_LEAVE:
				parseGroupLeave(cp);
				curState = ClientState.End;
				succ = true;
				break;

			case ProtocolType.DATA:
				parseData(cp);
				succ = true;
				break;
			}

			break;

		case End:
			break;
		}

		if (!succ) {
			MLog.i(DEBUG, TAG, "+++[Wrong Msg] unregnized message = " + cp.protocolNum);
		} else {
			MLog.i(DEBUG, TAG, "+++[" + GetTime.getTimeShort() + "][END], New State = " + curState);
		}

		return succ;
	}

	protected void parseAudio(BeanPacket cp) {

		MLog.i(DEBUG, TAG, "[Command] ParseAudio");

		StreamBeanParser protocol = new StreamBeanParser();
		protocol.setAudioData(cp.data);
		protocol.setProtocolNum(ProtocolType.AUDIO);

		ClientHub.getInstance().sendMsgToGroupExceptSelf(getClient(), protocol);

	}

	protected void parsePing(BeanPacket cp) {
		Gson gson = new Gson();
		Ping ping = gson.fromJson(cp.turnToContent, Ping.class);
		MLog.i(DEBUG, TAG, "心跳id+预留信息：" + ping.getPingid() + ping.getReserved());

		BlockBeanParser protocol = new BlockBeanParser();
		protocol.setData(cp.turnToContent);
		protocol.setProtocolNum(ProtocolType.PING);

		ClientHub.getInstance().sendMsgToClient(getClient(), protocol);
	}

	protected void parseData(BeanPacket cp) {
		Gson gson = new Gson();
		Data data = gson.fromJson(cp.turnToContent, Data.class);
		MLog.i(DEBUG, TAG, "Client(ID=" + getClient().getClientID() + "：" + data.toString());

		BlockBeanParser protocol = new BlockBeanParser();
		protocol.setData(cp.turnToContent);
		protocol.setProtocolNum(ProtocolType.DATA);

		// call ClientHub
		ClientHub.getInstance().sendMsgToGroupExceptSelf(getClient(), protocol);

	}

	@Override
	public Client getClient() {
		return this.client;
	}

	protected void parseGroupLeave(BeanPacket cp) {

		Map<String, Boolean> backdate = new HashMap<String, Boolean>();
		backdate.put("status", true);
		Gson gson = new Gson();
		String temp = gson.toJson(backdate);

		BlockBeanParser protocol = new BlockBeanParser();
		protocol.setProtocolNum(ProtocolType.GROUP_DISMISS);
		Data data = new Data();
		data.setPattion(3);
		data.setData(temp);

		String temp2 = gson.toJson(data);
		protocol.setData(temp2);

		ClientHub.getInstance().sendMsgToClient(client, protocol);
		MLog.i(DEBUG, TAG, "[Command] RemoveTourist");

	}

	// protected void parseSelectGuide(BeanPacket cp) {
	//
	// MLog.i(DEBUG, TAG, "[Command] SelectGuide");
	//
	// Gson gson = new Gson();
	// GroupJoin sel = gson.fromJson(cp.turnToContent, GroupJoin.class);
	//
	// MLog.i(DEBUG, TAG, "服务器接收到的导游ID为：" + sel.getGuideid());
	//
	// GroupJoin sg = new GroupJoin();
	// List<Client> list = ClientHub.getInstance().onGuideList();
	// for (Client wh : list) {
	// if (wh.getClientID() == sel.getGuideid()) {
	// // 根据选择的导游加到该导游对应的群组
	// ClientHub.getInstance().onSelectGuide(getClient(), sel.getGuideid());
	// sg.setStatus(100);// 请求成功
	// sg.setGroupid(sel.getGuideid());
	// sg.setGuideid(sel.getGuideid());
	// } else {
	// sg.setStatus(101);
	// }
	// }
	//
	// String temp = gson.toJson(sg);
	//
	// BlockBeanParser protocol = new BlockBeanParser();
	// protocol.setProtocolNum(ProtocolType.GROUP_JOIN);
	// protocol.setData(temp);
	//
	// ClientHub.getInstance().sendMsgToClient(getClient(), protocol);
	//
	// }

	protected void parseGuideBegin(BeanPacket cp) {

		MLog.i(DEBUG, TAG, "[Command] TeacherBegin");
		// call ClientHub
		// this operation always can be successful;
		ClientHub.getInstance().onGuideBegin(getClient());

		BlockBeanParser protocol = new BlockBeanParser();
		protocol.setData(cp.turnToContent);
		protocol.setProtocolNum(ProtocolType.GROUP_CREATE_AND_JOIN);
		MLog.i(DEBUG, TAG, "协议号：" + protocol.getProtocolNum());
		System.out.println("协议号：" + protocol.getProtocolNum());

		ClientHub.getInstance().sendMsgToClient(getClient(), protocol);

	}

	protected void parseGroupDismiss(BeanPacket cp) {

		MLog.i(DEBUG, TAG, "[Command] RemoveGuide");

		Map<String, Boolean> backdate = new HashMap<String, Boolean>();
		backdate.put("status", true);

		Gson gson = new Gson();
		String temp = gson.toJson(backdate);

		BlockBeanParser protocol = new BlockBeanParser();
		protocol.setProtocolNum(ProtocolType.GROUP_DISMISS);
		Data data = new Data();
		data.setPattion(3);
		data.setData(temp);

		String temp2 = gson.toJson(data);
		protocol.setData(temp2);

		ClientHub.getInstance().sendMsgToClient(client, protocol);

		BlockBeanParser toTourist = new BlockBeanParser();
		toTourist.setProtocolNum(ProtocolType.GROUP_LEAVE);
		Data data2 = new Data();
		data2.setPattion(3);
		data2.setData(temp);

		String temp3 = gson.toJson(data2);

		toTourist.setData(temp3);

		// call ClientHub
		ClientHub.getInstance().sendMsgToGroupExceptSelf(getClient(), toTourist);

	}

	@Override
	public void onDisconnected() {
		ClientHub.getInstance().onClientDisconnect(client);

	}

}
