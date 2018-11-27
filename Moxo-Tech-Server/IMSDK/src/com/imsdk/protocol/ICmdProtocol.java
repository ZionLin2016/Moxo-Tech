package com.imsdk.protocol;

import com.imsdk.beanparser.BeanPacket;

public interface ICmdProtocol {

	// return whether the command has been successfully processed.
	boolean onCmd(BeanPacket cp);

	Client getClient();

	void onDisconnected();

}
