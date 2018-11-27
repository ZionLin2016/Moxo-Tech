package com.chat.protocol;

import java.net.Socket;
import java.util.ArrayList;

import com.imsdk.beanparser.BasicBeanParser;
import com.imsdk.protocol.Client;
import com.imsdk.util.MLog;
import com.moxo.model.Membership;

public class ClientHub {

	private static boolean DEBUG = false;
	private static String TAG = "ClientHub";

	private static ClientHub instance = null;

	synchronized public static ClientHub getInstance() {
		if (instance == null) {
			instance = new ClientHub();
		}

		return instance;
	}

	public Membership membership = new Membership();

	public void onGuideBegin(Client guide) {
		membership.guideCreateOrRejoinGroup(guide);
	}

	// public boolean onSelectGuide(Client sender, Integer guideID) {
	//
	// MLog.i(DEBUG, TAG, "Before processing [selectGuide] : ");
	// membership.printMappingGuide2Tst();
	//
	// /*
	// * the Guide ID may become invalid because the guide has exited
	// */
	// Integer groupID = guideID;
	// GroupModel group = membership.getGroupByID(groupID);
	// if (group == null) {
	// return false;
	// }
	//
	// membership.joinGroup(sender, group);
	//
	// MLog.i(DEBUG, TAG, "After processing [selectGuide] : ");
	// membership.printMappingGuide2Tst();
	//
	// return true;
	// }

	public void closeGroup(Integer groupID) {
		// TODO Auto-generated method stub

	}

	public ArrayList<Client> onGuideList() {
		return membership.getAllGuide();
	}

	// 添加发送信息发送给別的客户端
	public void sendMsgToGroupExceptSelf(Client sender, BasicBeanParser data) {

		membership.printMappingGuide2Tst();

		for (Client each : membership.getAllInSameGroup(sender)) {
			MLog.i(DEBUG, TAG, "all member in same group = " + each.getUserName() + " id=" + each.getClientID() + " ip="
					+ each.getUserIP() + " port = " + each.getUserPort());
			if (!each.equals(sender)) {
				sendMsgToClient(each, data);
			}
		}
	}

	public void sendMsgToClient(Client sender, BasicBeanParser data) {
		if (sender == null) {
			return;
		}

		try {
			MLog.i(DEBUG, TAG,
					"Send data to Client " + sender.getUserName() + " its queue size=" + sender.getSizeOfQueueToSend());
			sender.sendMessage(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onClientDisconnect(Client client) {
		membership.removeClient(client);
	}

	public void onClientConnect(Client client, Socket socket) {
		// if it is a guide, try to join the same group as it exited last time
		client.onConnect(socket);
	}

	public boolean tryLoginUniquely(Client client) {
		return membership.addClient(client);
	}

	public void onClientDisconnected(Client client) {
		membership.removeClient(client);

	}

	public void disconnectClient(Client client) {
		client.stop();
	}

	public void disconnectAllClient() {
		for (Client each : membership.getAllClient()) {
			each.stop();
		}

		membership.dismissAndClearAllRelationship();
	}

}