package com.closedevice.fastapp.IM.server;

import com.closedevice.fastapp.IM.protocol.BasicProtocol;

import java.util.ArrayList;


public class ClientHub {
	
	public static TourMembership membership = new TourMembership();
	
	public static void onGuideBegin(Client guide)
	{
		membership.guideCreateOrRejoinGroup(guide);
	}

	public static boolean onSelectGuide(Client sender, Integer guideID)
	{

		System.out.println("Before processing [selectGuide] : ");
		membership.printMappingGuide2Tst();	 

		/*
		 * the Guide ID may become invalid because the guide has exited
		 */
		Integer groupID = guideID;
		Group group = membership.getGroupByID(groupID);
		if (group == null)
		{
			return false; 
		}
		
		membership.joinGroup(sender, group);

		System.out.println("After processing [selectGuide] : ");
		membership.printMappingGuide2Tst();	 
		
		return true;
	}
	public static void closeGroup(Integer groupID) {
		// TODO Auto-generated method stub
		
	}
	
	public static ArrayList<Client> onGuideList()
	{
		return membership.getAllGuide(); 
	}
	
	// 添加发送信息发送给別的客户端
	public static void sendMsgToGroupExceptSelf(Client sender,
			BasicProtocol data) {

		membership.printMappingGuide2Tst();
		
		for (Client each : membership.getAllInSameGroup(sender)) {
			if (!each.equals(sender)) {
				each.sendMessage(data);
			}
		}
	}
	
	public static void sendMsgToClient(Client sender, BasicProtocol data)
	{
		if (sender == null)
		{
			return;
		}
		
		try{
			sender.sendMessage(data);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	static public void onClientDisconnect(Client client)
	{
		membership.removeClient(client);
	}
	
	static public void onClientConnect(Client client)
	{
		//if it is a guide, try to join the same group as it exited last time
		client.onConnect();
	}
	
	static public void afterAvoidCall(Client client)
	{
		membership.addClient(client);	
	}

	public static void onClientDisconnected(Client client)
	{
		membership.removeClient(client);
	}
	
	public static void disconnectClient(Client client)
	{
		client.disconnect();
	}
	
	public static void disconnectAllClient() {
		for (Client each: membership.getAllClient() )
		{
			each.disconnect();
		}
		
		membership.dismissAndClearAllRelationship();
	}
	
 

}