package com.closedevice.fastapp.IM.models;

public class TouristOut {
	private int userid;

	private int guideid;

	private boolean status;

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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TouristOut [userid=" + userid + ", guideid=" + guideid + ", status=" + status + "]";
	}

}
