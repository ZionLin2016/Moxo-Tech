package com.imsdk.bean;

public class GroupJoin {
	private int userid; // 用户id
	private int guideid; // 选择的导游id

	private int groupid; // 群id
	private int status; // 状态信息

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getGuideid() {
		return guideid;
	}

	public void setGuideid(int guideid) {
		this.guideid = guideid;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "GroupJoin [userid=" + userid + ", guideid=" + guideid + ", groupid=" + groupid + ", status=" + status
				+ "]";
	}

}
