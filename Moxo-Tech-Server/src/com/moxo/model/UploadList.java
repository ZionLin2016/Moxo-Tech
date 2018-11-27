package com.moxo.model;

import java.util.List;

public class UploadList {
	private List<UploadMsg> content;

	public List<UploadMsg> getContent() {
		return content;
	}

	public void setContent(List<UploadMsg> content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "UploadList [content=" + content + "]";
	}

}
