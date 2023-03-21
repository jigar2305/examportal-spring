package com.Bean;

public class ResponseBean {
	private Object data;
	private String msg;
	private Integer apicode;
	private Exception exception;
	
	public ResponseBean() {
		super();
	}

	public ResponseBean(String msg, Integer apicode) {
		super();
		this.msg = msg;
		this.apicode = apicode;
	}
	
	public ResponseBean(Exception exception, String msg, Integer apicode) {
		super();
		this.exception = exception;
		this.msg = msg;
		this.apicode = apicode;
	}

	public ResponseBean(Object data, String msg, Integer apicode) {
		super();
		this.data = data;
		this.msg = msg;
		this.apicode = apicode;
	}
	public ResponseBean(Object data, String msg) {
		super();
		this.data = data;
		this.msg = msg;
		this.apicode = null;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getApicode() {
		return apicode;
	}

	public void setApicode(Integer apicode) {
		this.apicode = apicode;
	}

}
