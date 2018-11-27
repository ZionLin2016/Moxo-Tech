package com.moxo.model;

import java.io.Serializable;

public class Message<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private int code;
	private String msg;
	private T results;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getResults() {
		return results;
	}

	public void setResults(T results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "Message [code=" + code + ", msg=" + msg + ", results=" + results + "]";
	}

}
