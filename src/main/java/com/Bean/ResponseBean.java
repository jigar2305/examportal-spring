package com.Bean;


public class ResponseBean<T>{
	private T data;
	private String msg;
	private Integer apicode;
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
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
