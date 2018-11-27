package com.imsdk.beanparser;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class BlockBeanParser extends BasicBeanParser implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int PROTOCOL_TYPE = 0;

	private String data;// 正文数据

	@Override
	public int getLength() {
		return HEADID_LEN + PACKEAGE_LEN + PROTOCOL_LEN + data.getBytes(Charset.forName("UTF-8")).length;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	// 拼接发送数据
	@Override
	public byte[] sendAllData() throws Exception {
		byte[] base = super.sendAllData();
		byte[] data = this.data.getBytes(Charset.forName("UTF-8"));

		ByteArrayOutputStream baos = new ByteArrayOutputStream(getLength());
		baos.write(base, 0, base.length);
		baos.write(data, 0, data.length);
		byte[] send = baos.toByteArray();

		return send;
	}

	// 解析接收数据
	@Override
	public List<Integer> parseData(byte[] data) {
		ArrayList<Integer> list = (ArrayList<Integer>) super.parseData(data);

		try {
			int start = HEADID_LEN + PACKEAGE_LEN + PROTOCOL_LEN;
			int length = data.length - HEADID_LEN - PACKEAGE_LEN - PROTOCOL_LEN;
			System.out.println(String.format("start = %d, length = %d", start, length)); 
			this.data = new String(data, start, length
					, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 
		return list;
	}

	@Override
	public String toString() {
		return "data:" + data;
	}

}
