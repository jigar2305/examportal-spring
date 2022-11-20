package com.Bean;

import java.util.List;

import com.Entity.SubjectBean;
import com.Entity.SubjectFileBean;

public class Getsubjectfile {
	
	private SubjectBean subject;
	
	private List<SubjectFileBean> files;

	public SubjectBean getSubject() {
		return subject;
	}

	public void setSubject(SubjectBean subject) {
		this.subject = subject;
	}

	public List<SubjectFileBean> getFiles() {
		return files;
	}

	public void setFiles(List<SubjectFileBean> files) {
		this.files = files;
	}
	

}
