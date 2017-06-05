package com.closedevice.fastapp.IM.util;

import com.closedevice.fastapp.IM.protocol.BasicProtocol;
import com.closedevice.fastapp.IM.server.Client;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;


public class SocketUtil {

	// 读数据
	public static ChatPacket readFromStream(Client client, InputStream inputStream) throws IOException {
		
		Integer clientID = client.getClientID(); 
		if (clientID == null)
		{
			clientID = -1; 
		}
		
		DataInputStream dins = (DataInputStream) inputStream;

		ChatPacket re = new ChatPacket();

		re.headId = dins.readInt();
		re.size = dins.readInt();
		re.protocolNum = dins.readInt();
		re.data = new byte[re.size - 4 - 4 - 4];
		dins.readFully(re.data);

		re.turnToContent = new String(re.data, "UTF-8");

		return re;
	}

	// 写数据
	public static void writeToStream(BasicProtocol basicProtocol, DataOutputStream outputStream) throws Exception {

		byte[] buffData = basicProtocol.sendAllData();
		try {
			outputStream.write(buffData);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 关闭输入流
	public static void closeInputStream(InputStream is) {
		try {
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 关闭输出流
	public static void closeOutputStream(OutputStream os) {
		try {
			if (os != null) {
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "SocketUtil [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}