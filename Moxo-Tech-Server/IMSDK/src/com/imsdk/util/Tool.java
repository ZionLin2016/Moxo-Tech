package com.imsdk.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class Tool {

	// int 转字节数组
	public static byte[] intToByteArrays(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

	public static byte[] int2ByteArrays(int i) throws Exception {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(buf);
		out.writeInt(i);
		byte[] b = buf.toByteArray();
		out.close();
		buf.close();
		return b;
	}

	// 字节数组转int
	public static int byteArray2Int(byte[] b) {
		int intValue = 0;
		for (int i = 0; i < b.length; i++) {
			intValue += (b[i] & 0xFF) << (8 * (3 - i));
		}
		return intValue;
	}

	public static int byteArrayToInt(byte[] b) throws IOException {
		ByteArrayInputStream byteInput = new ByteArrayInputStream(b);
		DataInputStream dataInput = new DataInputStream(byteInput);
		int n = dataInput.readInt();
		return n;
	}

	public static int byteArrayToInt2(byte[] b, int byteOffset, int byteCount) {
		int intValue = 0;
		for (int i = byteOffset; i < (byteOffset + byteCount); i++) {
			intValue += (b[i] & 0xFF) << (8 * (3 - (i - byteOffset)));
		}
		return intValue;
	}

	// 字节数组转String
	public static String byteArrays2String(byte[] bytes) {

		StringBuffer sbuf = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			sbuf.append(bytes[i]);
		}
		return sbuf.toString();
	}

	public static byte[] string2ByteArrays(String text) {
		byte[] bytes = new byte[1024];
		bytes = text.getBytes();
		return bytes;
	}

	public static String convertStreamToString(InputStream in)
			throws UnsupportedEncodingException {

		byte[] cha = new byte[1024];
		int len = 0;
		try {
			len = in.read(cha);
			System.out.println(new String(cha, 0, len, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new String(cha, 0, len, "UTF-8");
	}

}
