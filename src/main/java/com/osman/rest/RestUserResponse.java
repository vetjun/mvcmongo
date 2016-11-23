package com.osman.rest;
/*This class returns error or success mesage for one ajax calling*/
public class RestUserResponse {
	private boolean success;
	private String message;
	
	public RestUserResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
