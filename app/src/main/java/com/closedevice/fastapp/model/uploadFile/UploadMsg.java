package com.closedevice.fastapp.model.uploadFile;

public class UploadMsg {
	private boolean status;
	private String message;
	private String resource_address;

	public UploadMsg(boolean status, String message, String resource_address) {
		this.status = status;
		this.message = message;
		this.resource_address = resource_address;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResource_address() {
		return resource_address;
	}

	public void setResource_address(String resource_address) {
		this.resource_address = resource_address;
	}

	@Override
	public String toString() {
		return "UploadMsg{" +
				"status=" + status +
				", message='" + message + '\'' +
				", resource_address='" + resource_address + '\'' +
				'}';
	}
}
