package com.rohith.springreactive.fluxandmono;

@SuppressWarnings("serial")
public class CustomException extends Throwable{
	private String message;
	
	public CustomException(Throwable e) {
		this.setMessage(e.getMessage());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
