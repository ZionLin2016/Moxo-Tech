package cn.lsd.app.IM.server;

import java.io.Serializable;

public class Group implements Serializable, Comparable {
	private static final long serialVersionUID = 3L;

	private int groupId;// 群ID
	  
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	@Override
	public int compareTo(Object arg0) {
		return this.groupId - ((Group)arg0).groupId;
	}

}
