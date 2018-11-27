package cn.lsd.app.IM.models;

public class GuideList {
	private String reserved; // 预留信息

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	@Override
	public String toString() {
		return "AskGuideList [reserved=" + reserved + "]";
	}

}
