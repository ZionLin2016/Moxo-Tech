package com.imsdk.beanparser;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.imsdk.util.Tool;

public abstract class BasicBeanParser {

	protected static final int HEADID_LEN = 4;// 包头标识长度
	protected static final int PACKEAGE_LEN = 4;// 包长
	protected static final int PROTOCOL_LEN = 4;

	private static final int HEAD = 666;
	private int protocolNum;
	private long time;

	public int getLength() {
		return HEADID_LEN + PACKEAGE_LEN + PROTOCOL_LEN;
	}

	public int getHead() {
		return HEAD;
	}

	public int getProtocolNum() {
		return protocolNum;
	}

	public void setProtocolNum(int protocolNum) {
		this.protocolNum = protocolNum;
	}

	// 拼接发送数据
	public byte[] sendAllData() throws Exception {
		byte[] head = Tool.intToByteArrays(getHead());
		byte[] pack = Tool.intToByteArrays(getLength());
		byte[] type = Tool.intToByteArrays(getProtocolNum());

		ByteArrayOutputStream baos = new ByteArrayOutputStream(getLength());
		baos.write(head, 0, HEADID_LEN);
		baos.write(pack, 0, PACKEAGE_LEN);
		baos.write(type, 0, PROTOCOL_LEN);
		byte[] send = baos.toByteArray();

		return send;
	}

	// 解析接收数据
	public List<Integer> parseData(byte[] data) {
		int head = Tool.byteArrayToInt2(data, 0, HEADID_LEN);
		int pack = Tool.byteArrayToInt2(data, HEADID_LEN, PACKEAGE_LEN);
		int type = Tool.byteArrayToInt2(data, PACKEAGE_LEN, PROTOCOL_LEN);

		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(head);
		list.add(pack);
		list.add(type);
		return list;
	}

	private long getTime() {
		return time;
	}

	private void setTime(long time) {
		this.time = time;
	}

}
