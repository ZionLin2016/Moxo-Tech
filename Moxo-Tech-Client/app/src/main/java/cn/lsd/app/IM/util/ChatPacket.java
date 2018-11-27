package cn.lsd.app.IM.util;

public class ChatPacket {

	public int headId;
	public int size;
	public int protocolNum;
	public byte[] data = null;

	public String turnToContent;
}
