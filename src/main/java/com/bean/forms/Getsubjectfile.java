package com.bean.forms;

import java.util.List;

public class Getsubjectfile {
	
	private SubjectBean subject;
	
	private List<PdfBean> files;

	public SubjectBean getSubject() {
		return subject;
	}

	public void setSubject(SubjectBean subject) {
		this.subject = subject;
	}

	public List<PdfBean> getFiles() {
		return files;
	}

	public void setFiles(List<PdfBean> files) {
		this.files = files;
	}
	

}
