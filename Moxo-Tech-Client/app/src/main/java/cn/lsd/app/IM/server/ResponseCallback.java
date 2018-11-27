package cn.lsd.app.IM.server;

public interface ResponseCallback {

	// void targetIsOffline(DataProtocol reciveMsg); //离线消息

	void targetIsOnline(String clientIp);
}
