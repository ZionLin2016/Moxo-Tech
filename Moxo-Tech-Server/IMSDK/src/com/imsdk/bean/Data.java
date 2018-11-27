package com.imsdk.bean;

public class Data {
	private int type; // 0文本，1图片链接，2视频链接， 3命令
	private int pattion;
	private String data; // 内容

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPattion() {
		return pattion;
	}

	public void setPattion(int pattion) {
		this.pattion = pattion;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Data [pattion=" + type + ", data=" + data + "]";
	}

}
