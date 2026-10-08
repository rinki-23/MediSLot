package com.medislot.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

	private LocalDateTime timeStamp;
	private int status;
	private String error;
	private String message;
	private String path;
	
	public ErrorResponse(int status, String error, String message, String path) {
		super();
		this.timeStamp = LocalDateTime.now();
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}
	
	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	

	public int getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}
	@Override
	public String toString() {
		return "ErrorResponse [timeStamp=" + timeStamp + ", status=" + status + ", error=" + error + ", message="
				+ message + ", path=" + path + "]";
	}
	
	
	
}
