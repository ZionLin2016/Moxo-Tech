package com.closedevice.fastapp.IM.models;

public class TeacherBegin {
	private int userid;// 导游id
	private int groupid;// 群id
	private String reserved;// 预留信息

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	@Override
	public String toString() {
		return "TeacherBegin{" +
				"userid=" + userid +
				", groupid=" + groupid +
				", reserved='" + reserved + '\'' +
				'}';
	}
}
