package com.chat.protocol;

public class ProtocolType {

	// for login:
	// public static final int LOGIN = 0;

	// for data
	public static final int DATA = 4;

	public static final int AUDIO = 8;

	public static final int PING = 7;

	// for membership
	public static final int GROUP_LIST = 2;

	public static final int GROUP_JOIN = 3;

	public static final int GROUP_LEAVE = 6;

	public static final int GROUP_DISMISS = 5;

	public static final int GROUP_CREATE_AND_JOIN = 1;

	public static String getMsgName(int value) {
		switch (value) {

		// case LOGIN:
		// return "LOGIN";
		case DATA:
			return "DATA";
		case AUDIO:
			return "AUDIO";
		case PING:
			return "PING";
		case GROUP_LIST:
			return "GROUP_LIST";
		case GROUP_JOIN:
			return "GROUP_JOIN";
		case GROUP_LEAVE:
			return "GROUP_LEAVE";
		case GROUP_DISMISS:
			return "GROUP_DISMISS";
		case GROUP_CREATE_AND_JOIN:
			return "GROUP_CREATE_AND_JOIN";
		default:
			return "~~~~Unrecognized Message~~~~~~~ value=" + value;
		}
	}

}
