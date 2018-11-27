package com.imsdk.beanparser;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class StreamBeanParser extends BasicBeanParser implements Serializable {

	private static final long serialVersionUID = 1L;

	private byte[] audioData;// 音频数据

	public int getLength() {
		return HEADID_LEN + PACKEAGE_LEN + PROTOCOL_LEN + audioData.length;
	}

	public byte[] getAudioData() {
		return audioData;
	}

	public void setAudioData(byte[] audioData) {
		this.audioData = audioData;
	}

	// 拼接发送数据
	@Override
	public byte[] sendAllData() throws Exception {
		byte[] base = super.sendAllData();
		byte[] data = this.getAudioData();

		ByteArrayOutputStream baos = new ByteArrayOutputStream(getLength());
		baos.write(base, 0, super.getLength());
		baos.write(data, 0, data.length);
		byte[] send = baos.toByteArray();

		return send;
	}

	// 解析接收数据
	@Override
	public List<Integer> parseData(byte[] data) {

		ArrayList<Integer> list = (ArrayList<Integer>) super.parseData(data);
		return list;
	}

	@Override
	public String toString() {
		return "data:" + audioData;
	}

}
