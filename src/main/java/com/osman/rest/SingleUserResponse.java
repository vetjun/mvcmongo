package com.osman.rest;

import com.osman.model.User;
/*This class returns ajax calling message and user is related it.*/
public class SingleUserResponse {
	private boolean success;
	private User issuer;
	
	public SingleUserResponse(boolean success, User issuer) {
		this.success = success;
		this.issuer = issuer;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public User getIssuers() {
		return issuer;
	}
	public void setIssuer(User issuer) {
		this.issuer = issuer;
	}
}
