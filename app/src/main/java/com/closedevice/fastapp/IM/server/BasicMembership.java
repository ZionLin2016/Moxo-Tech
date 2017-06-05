package com.closedevice.fastapp.IM.server;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class BasicMembership {
	
	private static Object setLock = new Object();
	/*
	 * for network abstract
	 */
	protected ConcurrentHashMap<Integer, Client> mapID2Client = new ConcurrentHashMap<Integer, Client>();

	protected ConcurrentHashMap<Integer, Group> mapGroupID2Group = new  ConcurrentHashMap<Integer, Group>();
	
	/*
	 * general IM abstract
	 */
	protected ConcurrentHashMap<Client, Group> mapClient2Group = new ConcurrentHashMap<Client, Group>();
	
	protected ConcurrentHashMap<Group, ConcurrentSkipListSet<Client>>
		mapGroup2Memberset = new ConcurrentHashMap<Group, ConcurrentSkipListSet<Client>>();
	
	protected ConcurrentSkipListSet<Group> allGroup = new ConcurrentSkipListSet<Group>();

	protected ConcurrentSkipListSet<Client> allClient = new ConcurrentSkipListSet<Client>();
	
	

	synchronized public Group getGroup(Client client)
	{
		return mapClient2Group.get(client);
	}
	
	synchronized public Group getGroupByID(Integer id)
	{
		return mapGroupID2Group.get(id);
	}
	
	synchronized public Client getClientByID(Integer id)
	{
		return mapID2Client.get(id);
	}
	
	synchronized public Client[] getAllInSameGroup(Client client)
	{
		
		 Group group = getGroup(client);
		 if (group == null)
		 {
			 return new Client[0];
		 }
		 ConcurrentSkipListSet<Client> members = mapGroup2Memberset.get(group);
		 if (members == null)
		 {
			 return new Client[0];
		 }
		 
		 return members.toArray(new Client[0]);
	}
	
	synchronized public boolean isMemberInGroup(Client client, Group group)
	{
		Group _group = mapClient2Group.get(client);
		if (_group == null)
		{
			return false; 
		}else{
			return _group.equals(group);
		}
	}
	 
	
	synchronized public Client[] getGroupMembers(Group group)
	{
		ConcurrentSkipListSet<Client> members = mapGroup2Memberset.get(group);
		if(members == null)
		{
			return new Client[0];
		}else{
			return members.toArray(new Client[0]);
		}
		
	}

	
	synchronized public void addGroup(Group newGroup) {
		if (!allGroup.contains(newGroup))
		{
			//two relationships
			allGroup.add(newGroup);
			mapGroupID2Group.put(newGroup.getGroupId(), newGroup);
			
			//maintain a group's members
			ConcurrentSkipListSet<Client> members = new ConcurrentSkipListSet<Client>();
			mapGroup2Memberset.put(newGroup, members);
			
		}
	}
	
	synchronized public void dismissAndRemoveGroup(Group group)
	{
		if (!allGroup.contains(group))
		{
			return;
		}
		
		//dismiss
		ConcurrentSkipListSet<Client> members = mapGroup2Memberset.get(group);
		if (members!=null)
 {
			for (Client member : members) {
				mapClient2Group.remove(member);
			}
			members.clear();
			mapGroup2Memberset.remove(group);
		}
		
		//remove group
		allGroup.remove(group);
		mapGroupID2Group.remove(group.getGroupId());
	}
	
	synchronized public void removeClient(Client client)
	{
		
		if (!allClient.contains(client))
		{
			return;
		}
		
		//leave group first. 
		leaveGroup(client);
		
		allClient.remove(client);
		mapID2Client.remove(client.getClientID());
		
	}
	
	 
	
	synchronized public Client[] getAllClient()
	{
		return allClient.toArray(new Client[0]);
	}
	
	synchronized public Group[] getAllGroup()
	{
		return allGroup.toArray(new Group[0]);
	}
	
	synchronized public void addClient(Client newClient) {
		System.out.println("add new client "+newClient.getClientID()); 
		if (!allClient.contains(newClient))
		{
			//two relationships
			allClient.add(newClient);
			mapID2Client.put(newClient.getClientID(), newClient);
		}
	}
	
	synchronized public void joinGroup(Client client,
			Group group) {
		
		System.out.println("joinGroup: client="+client.getClientID()+" group="+group.getGroupId());
		
		if (!allGroup.contains(group))
		{
			System.out.println("You can't join a client to a group that is not yet maintained");
			return;
		}
		
		//two-way relationship:
		//1st way:
		mapClient2Group.put(client, group);
		//2nd way:
		ConcurrentSkipListSet<Client> members = mapGroup2Memberset.get(group);
		if (members==null)
		{
			System.out.println("You can't join a client to a group the memberset of which is not yet maintained");
			return;
		}
		members.add(client);
		
	}
	
	synchronized public void leaveGroup(Client client) {
		Group group = mapClient2Group.get(client);
		if (group == null)
		{
			return; 
		}
		
		//two-way relationship
		ConcurrentSkipListSet<Client> members = mapGroup2Memberset.get(group);
		members.remove(client);
		mapClient2Group.remove(client);
		
	}
	
	synchronized public void dismissAndClearAllRelationship()
	{
		allClient.clear();
		allGroup.clear();
		mapClient2Group.clear();
		mapGroupID2Group.clear();
		mapID2Client.clear();
		for (Entry<Group, ConcurrentSkipListSet<Client>> each: mapGroup2Memberset.entrySet())
		{
			each.getValue().clear();
		}
		mapGroup2Memberset.clear();
	}
	
	
}
