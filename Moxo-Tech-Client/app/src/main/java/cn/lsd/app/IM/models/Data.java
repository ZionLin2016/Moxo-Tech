package cn.lsd.app.IM.models;

public class Data {
	private int pattion; // 0文本，1语音，2视频， 3实时视频

	private String data; // 内容

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
		return "Data [pattion=" + pattion + ", data=" + data + "]";
	}

}
