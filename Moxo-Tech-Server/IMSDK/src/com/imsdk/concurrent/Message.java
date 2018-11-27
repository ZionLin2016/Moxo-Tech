package com.imsdk.concurrent;

public class Message {

	public final int what;
	public final Object param;
	
	public Message(int what)
	{
		this.what = what;
		param = null; 
	}
	
	public Message(int what, Object param)
	{
		this.what = what;
		this.param = param; 
	}
	
	
	
}
