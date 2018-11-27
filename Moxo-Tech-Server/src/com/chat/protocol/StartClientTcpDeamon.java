package com.chat.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import com.imsdk.protocol.Client;

public class StartClientTcpDeamon implements Runnable {

	private static boolean isStart = true;

	@Override
	public void run() {
		InputStream in = StartClientTcpDeamon.class.getClassLoader().getResourceAsStream("config.properties");
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e1) {
			System.err.println("属性文件加载异常");
			e1.printStackTrace();
		}

		ServerSocket serverSocket = null;
		int port = Integer.parseInt(properties.getProperty("socket.port"));
		try {

			serverSocket = new ServerSocket(port);
			while (isStart) {
				System.out.println("等待用户接入 : ");
				Socket socket = serverSocket.accept();
				System.out.println(String.format("A new client has come from %s:%d ",
						socket.getRemoteSocketAddress().toString(), socket.getPort()));

				Client newClient = new Client();
				newClient.setProcotol(new ClientProtocol(newClient));

				ClientHub.getInstance().onClientConnect(newClient, socket);
			}

			serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				try {
					isStart = false;
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ClientHub.getInstance().disconnectAllClient();
		}
	}

}
