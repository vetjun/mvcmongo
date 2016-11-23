package com.osman.rest;

import java.util.List;

import com.osman.model.User;
/*This class returns error messages with multiple user definitions.*/
public class MultipleUserResponse {
	private boolean success;
	private List<User> issuers;
	
	public MultipleUserResponse(boolean success, List<User> issuers) {
		this.success = success;
		this.issuers = issuers;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<User> getIssuers() {
		return issuers;
	}
	public void setIssuers(List<User> issuers) {
		this.issuers = issuers;
	}
}
