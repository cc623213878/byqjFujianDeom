package com.byqj.exception;


import com.byqj.security.core.support.ResponseData;

public class RequestFailureException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	private ResponseData responseData;

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}


}
