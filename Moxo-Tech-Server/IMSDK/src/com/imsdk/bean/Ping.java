package com.imsdk.bean;

public class Ping {
	private int pingid;
	private String reserved;

	public int getPingid() {
		return pingid;
	}

	public void setPingid(int pingid) {
		this.pingid = pingid;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	@Override
	public String toString() {
		return "Ping [pingid=" + pingid + ", reserved=" + reserved + "]";
	}

}
