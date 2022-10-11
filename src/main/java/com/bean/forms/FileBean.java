package com.bean.forms;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;


public class FileBean implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public FileBean(MultipartFile file) {
		super();
		this.file = file;
	}
	public FileBean() {
		
	}
	

}
