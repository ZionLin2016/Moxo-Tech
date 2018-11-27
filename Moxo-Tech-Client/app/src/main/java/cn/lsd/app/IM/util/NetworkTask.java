package cn.lsd.app.IM.util;

import java.io.IOException;
import java.net.*;

import android.util.Log;

import static cn.lsd.app.AppConstant.SERVER_IP;
import static cn.lsd.app.AppConstant.SERVER_PORT;

public class NetworkTask{


    private Socket mClientSocket = null;

    public NetworkTask() {
    }

    public boolean startConnect() {
        try {
            mClientSocket = new Socket();
            mClientSocket.connect(
                    new InetSocketAddress(SERVER_IP, SERVER_PORT));
            Log.d("Network", "服务器连接成功");
            if (mClientSocket.isConnected()) {
                return true;
            } else {
                return false;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Log.d("Network", "服务器地址无法解析");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Network", "Socket io异常");
        }
        return  false;
    }

    public Socket getSocket() {
        return mClientSocket;
    }
//	private Socket mClientSocket = null;
//	private boolean mIsConnected = false;
//
//
//	@Override
//	public void run() {
//		try {
//			mClientSocket = new Socket(SERVER_IP, SERVER_PORT);
//
//			Log.d("Network", "服务器连接成功");
//			Log.d("Network","地址--------------" + SERVER_IP + ":" + SERVER_PORT);
//			if (mClientSocket.isConnected()) {
//				mIsConnected = true;
//			} else {
//				mClientSocket.close();
//				mIsConnected = false;
//			}
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//			Log.d("Network", "服务器地址无法解析");
//		} catch (IOException e) {
//			e.printStackTrace();
//			Log.d("Network", "Socket io异常");
//		}
//	}
//
//	public boolean getIsConnected() {
//		return mIsConnected;
//	}
//
//	public Socket getSocket() {
//		return mClientSocket;
//	}
}
