package com.imsdk.bean;

public class Message {

	private boolean falg;
	private String status;

	public boolean isFalg() {
		return falg;
	}

	public void setFalg(boolean falg) {
		this.falg = falg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Message [falg=" + falg + ", status=" + status + "]";
	}

}
