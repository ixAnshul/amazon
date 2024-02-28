package com.amazon.amazon.response;

public class Response {
	private String message;
	private Object result;

	public Response() {
		super();
	}

	public Response(String message, Object result) {
		super();
		this.message = message;
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
