package com.imsdk.concurrent;

import java.util.concurrent.LinkedBlockingQueue;

public abstract class Handler {

	private Thread thread; 
	private final String name;
	
	private LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
	private volatile boolean isStop = false;
	private class InnerCmd
	{
		public final int what; //0: stop;
		public InnerCmd(int what)
		{
			this.what = what; 
		}
		
	}
	
	public Handler()
	{
		this.name = "Handler_"+System.currentTimeMillis(); 
	}
	
	public Handler (String name)
	{
		this.name = name; 
	}
	
	synchronized public void start()
	{
		if (thread==null)
		{
			thread = new Thread(this.name);
			thread.start();
		}
	}
	
	public void sendMessage(Message msg)
	{
		queue.add(msg);
	}
	
	
	
	public void sendEmptyMessage(int what)
	{
		//TODO: use a object pool to reduce the overhead of JVM
		queue.add(new Message(what));
	}
	
	public void stop()
	{
		queue.add(new InnerCmd(0));
	}
	
	protected class HandlerThread extends Thread{
		@Override 
		public void run()
		{
			while(!isStop)
			{
				Object event = queue.poll();
				if (event == null) {

					yield();

				} else {
					try {
						if (event instanceof Message) {

							handleMessage((Message) event);

						} else if (event instanceof Runnable) {
							((Runnable) event).run();
						} else if (event instanceof InnerCmd) {
							InnerCmd cmd = (InnerCmd) event;
							switch (cmd.what) {
							case 0:
								isStop = true;
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				 
			}
		}
	}
	
	abstract public void handleMessage(Message msg); 
		
}
