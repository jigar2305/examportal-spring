package com.bean;

import com.bean.forms.ResultBean;

public class examstatusBean {
	
	private UserBean user;
	
	private String status;
	
	private ResultBean result;

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ResultBean getResult() {
		return result;
	}

	public void setResult(ResultBean result) {
		this.result = result;
	}

}
