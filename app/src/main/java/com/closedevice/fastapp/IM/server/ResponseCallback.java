package com.closedevice.fastapp.IM.server;

public interface ResponseCallback {

	// void targetIsOffline(DataProtocol reciveMsg); //离线消息

	void targetIsOnline(String clientIp);
}
