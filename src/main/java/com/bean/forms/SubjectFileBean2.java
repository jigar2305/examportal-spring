package com.bean.forms;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;


public class SubjectFileBean2{
	
	

	private SubjectBean subject;
	
	private List<MultipartFile> files;
	
	

	public SubjectBean getSubject() {
		return subject;
	}

	public void setSubject(SubjectBean subject) {
		this.subject = subject;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	
}
