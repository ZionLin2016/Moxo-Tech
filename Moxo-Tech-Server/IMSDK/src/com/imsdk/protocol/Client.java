package com.imsdk.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import com.imsdk.beanparser.BasicBeanParser;
import com.imsdk.beanparser.BeanPacket;
import com.imsdk.beanparser.BeanParserUtil;
import com.imsdk.util.GetTime;
import com.imsdk.util.MLog;

public class Client implements Comparable<Client> {

	private boolean DEBUG = true;
	private String TAG = "Client";

	private ThreadReceiveHandler threadReceiveHandler;
	private ThreadSendHander threadSendHander;

	private Socket socket;
	private DataOutputStream outputStream;
	private DataInputStream inputStream;

	protected int clientID;
	protected String userName;
	protected int groupID;
	protected ICmdProtocol protocol;
	private volatile boolean isLoopRecAndSend = true;

	private volatile ConcurrentLinkedQueue<BasicBeanParser> queueToSend = new ConcurrentLinkedQueue<BasicBeanParser>();
	protected CopyOnWriteArrayList<ReceiveObserver> msgObserverList = new CopyOnWriteArrayList<ReceiveObserver>();

	private String userHostAddress = null;

	public String getUserIP() {
		if (userHostAddress == null) {
			userHostAddress = socket.getInetAddress().getHostAddress();
		}
		return userHostAddress;
	}

	public String getUserPort() {
		return "null";
	}

	public void setProcotol(ICmdProtocol protocol) {
		this.protocol = protocol;
	}

	public void receiveAddObserver(ReceiveObserver observer) {
		if (msgObserverList.contains(observer)) {
			return;
		}

		msgObserverList.add(observer);
	}

	protected void sendToAllObservers(Object msg) {
		for (ReceiveObserver observer : msgObserverList) {
			try {
				observer.onIMMessage(msg);
			} catch (Exception e) {
				// do thing
			}
		}
	}

	public void receiveRemoveObserver(ReceiveObserver observer) {
		msgObserverList.remove(observer);
	}

	public Client() {

	}

	public void onConnect(Socket socket) {

		this.socket = socket;
		MLog.i(DEBUG, TAG, "用户IP/port：" + getUserIP() + ":" + getUserPort());

		try {

			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());

			// 开启接收线程
			threadReceiveHandler = new ThreadReceiveHandler();
			threadReceiveHandler.start();
			threadReceiveHandler.setPriority(Thread.MAX_PRIORITY);

			// 开启发送线程
			threadSendHander = new ThreadSendHander();
			threadSendHander.start();
			threadSendHander.setPriority(Thread.MAX_PRIORITY);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	synchronized private void onDisconnected() {

		if (!isLoopRecAndSend) {
			return;
		}

		isLoopRecAndSend = false;

		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		new Thread() {

			@Override
			public void run() {

				MLog.i(DEBUG, TAG, "onDisconnected() 1");

				protocol.onDisconnected();

				MLog.i(DEBUG, TAG, "onDisconnected() 2");

				if (threadSendHander != null) {
					try {
						threadSendHander.join();
					} catch (Exception e) {
						e.printStackTrace();
					}

					threadSendHander = null;
				}

				MLog.i(DEBUG, TAG, "onDisconnected() 3");

				if (threadReceiveHandler != null) {
					try {
						threadReceiveHandler.join();
					} catch (Exception e) {
						e.printStackTrace();
					}
					threadReceiveHandler = null;
				}

				MLog.i(DEBUG, TAG, "onDisconnected() 4");

				if (inputStream != null) {
					BeanParserUtil.closeInputStream(inputStream);
					inputStream = null;
				}

				if (outputStream != null) {
					BeanParserUtil.closeOutputStream(outputStream);
					outputStream = null;
				}

				MLog.i(DEBUG, TAG, "onDisconnected() 5");
			}
		}.start();

	}

	public void stop() {

		MLog.i(DEBUG, TAG, "\n[[[[[Disconecting.....Client " + this.clientID
				+ "　]]]]]\n");

		msgObserverList.clear();
		onDisconnected();
	}

	// 添加发送信息返回给自身
	public void sendMessage(BasicBeanParser selfData) {
		if (!isConnected()) {
			return;
		}

		MLog.i(DEBUG, TAG, "send Msg to Client " + getClientID());

		synchronized (queueToSend) {
			queueToSend.offer(selfData);
			queueToSend.notifyAll();
		}
		;// 有新增待发送数据，则唤醒发送线程
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

		private void ackWrongMsg() {
			MLog.i(DEBUG, TAG, "%%%%%%%%%% ackWrongMsg (TODO)");
		}

		@Override
		public void run() {
			while (isLoopRecAndSend) {
				if (!isConnected()) {
					break;
				}
				try {
					if (inputStream != null) {
						BeanPacket cp = BeanParserUtil
								.readFromStream(inputStream);

						// MLog.i(DEBUG, TAG,
						// "Client "+getClientID()+" receives Packet ("+cp.protocolNum+")");
						boolean succ = protocol.onCmd(cp);

						if (!succ) {
							ackWrongMsg();
						}

						// TODO: deal with the case when the state is END
					}
				} catch (EOFException e) {
					MLog.i(DEBUG, TAG, "客户端 sends EOF. Socket is closed");
					break;
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}

			}

			onDisconnected();

			MLog.i(DEBUG, null, null);
			MLog.i(DEBUG, TAG, "End of Thread: ThreadReceiveHandler");
		}

	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {

		return userName;

	}

	public Integer getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public int getSizeOfQueueToSend() {
		return queueToSend.size();
	}

	public class ThreadSendHander extends Thread {

		@Override
		public void run() {

			while (isLoopRecAndSend) {
				if (!isConnected()) {
					break;
				}

				BasicBeanParser selfpro = null;

				synchronized (queueToSend) {
					while (isLoopRecAndSend && selfpro == null) {
						try {
							selfpro = queueToSend.poll();
							if (selfpro == null) {
								queueToSend.wait(100);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();

						}
					}

					if (selfpro != null) {
						try {
							BeanParserUtil.writeToStream(selfpro, outputStream);

							MLog.i(DEBUG, TAG, null);
							MLog.d(DEBUG, TAG, String.format(
									"[Client %s] going a message",
									getClientID()));
							MLog.i(DEBUG,
									TAG,
									String.format(
											"[%s] ***服务器 sends to Client (%d): size=%d protocol=%d content=%s",
											GetTime.getTimeShort(),
											getClientID(), selfpro.getLength(),
											selfpro.getProtocolNum(),
											selfpro.toString()));

						} catch (EOFException e1) {
							MLog.i(DEBUG, TAG, "这是合法的，客户端已关闭");
							e1.printStackTrace();
							break;
						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}

				}

			}

			onDisconnected();

			MLog.i(DEBUG, TAG, null);
			MLog.i(DEBUG, TAG, "End of Thread: ThreadSendHander");

		}
	}

	@Override
	public int compareTo(Client o) {
		if (o == null) {
			return -1;
		}

		return this.clientID - ((Client) o).clientID;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Client) {
			return ((Client) obj).clientID == clientID;
		} else {
			return false;
		}
	}
}
