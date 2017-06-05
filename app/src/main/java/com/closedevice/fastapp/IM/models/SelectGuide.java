package com.closedevice.fastapp.IM.models;

public class SelectGuide {
	private int userid; // 用户id
	private int guideid; // 选择的导游id

	private int groupid; // 群id
	private String status; // 状态信息

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SelectGuide [userid=" + userid + ", guideid=" + guideid + ", groupid=" + groupid + ", status=" + status
				+ "]";
	}

}
