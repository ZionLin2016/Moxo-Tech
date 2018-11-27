package cn.lsd.app.IM.server;

import java.util.ArrayList;
import java.util.Iterator;


public class TourMembership extends BasicMembership {
	public boolean isGuide(Client client)
	{
		 if (client == null)
		 {
			 return false;
		 }
		
		 return allGroup.contains(client.getClientID());
	}
 
	public Client getGuideInGroup(Group group)
	{
		if (group == null)
		{
			return null;
		}
		
		Integer groupID = group.getGroupId();
		Integer guideID = groupID; //for this project, they are the same
		Client guide = mapID2Client.get(guideID);
		return guide;
	}
	
	public ArrayList<Client> getAllTstInGroup(Group group)
	{
		ArrayList<Client> re = new ArrayList<Client>();
		
		Client[] members = getGroupMembers(group);
		Client guide = getGuideInGroup(group);
		if (guide == null || members.length == 0)
		{
			return re;
		}
			
		for (Client member:members)
		{
			if (!member.equals(guide))
			{
				re.add(member);
			}
		}
		
		return re;
				
	}
	
	synchronized public ArrayList<Client> getAllGuide() {
		
		ArrayList<Client> re = new ArrayList<Client>();
		
		Iterator<Group> it=allGroup.iterator();
		while(it.hasNext())
		{
			Group group = it.next();
			Client guide = getGuideInGroup(group); 
			if (guide == null)
			{
				System.out.println("Found Group ("+group.getGroupId()+") where the Guide is offline");
			}else{
				System.out.println("Found Group ("+group.getGroupId()+") where the Guide ("+ guide.getClientID()+") is online");
				re.add(guide);
			}

		}
		
		return re;
	}
	
	synchronized public void guideCreateOrRejoinGroup(Client guide)
	{
		Group group = getGroupByID(guide.getClientID());
		
		//if the guide has NOT yet in a group, 
		if (group==null) {
			
			group = new Group();
			System.out.println("guideCreateOrRejoinGroup guide.ID="+guide.getClientID());
			group.setGroupId(guide.getClientID()); 
			
			addGroup(group);
		} 
		
		joinGroup(guide, group);
		
		printMappingGuide2Tst();

	}
	
	/*
	 * below are some advanced methods:
	 */
	public void printMappingGuide2Tst()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("MappingGuide2Tst: "); 
		for (Group group: getAllGroup())
		{
			Client guide = this.getGuideInGroup(group); 
			
			if (guide == null)
			{
				continue; 
			}
			
			sb.append("[ Guide "+  guide.getClientID()+": ");
			
			for (Client member : getAllTstInGroup(group))
			{
				sb.append(member.getClientID()+", ");
			}
			
			sb.append("]; "); 
		}
		
		
		System.out.println(sb.toString());
	}

}
