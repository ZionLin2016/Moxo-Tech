package com.closedevice.fastapp.IM.util;

public enum ProtocolType {
	GROUP_CREATE_AND_JOIN(1),  Data(4), GROUP_DISMISS(5), GROUP_LEAVE(6), Ping(
			7), AudioProtocol(8);

	private ProtocolType(int type) {
		this.type = type;
	}

	private int type;

	public int getType() {
		return type;
	}

}
