package com.bean.forms;

import java.io.File;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.web.multipart.MultipartFile;

public class FileBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private File file;

	private Integer subjectfileId;

	private SubjectBean subject;

	private String fileName;

	public File getFile() {
		return file;
	}

	public void setFile(File file1) {
		this.file = file1;
	}

	public FileBean(File file) {
		super();
		this.file = file;
	}

	public FileBean() {

	}

	public Integer getSubjectfileId() {
		return subjectfileId;
	}

	public void setSubjectfileId(Integer subjectfileId) {
		this.subjectfileId = subjectfileId;
	}

	public SubjectBean getSubject() {
		return subject;
	}

	public void setSubject(SubjectBean subject) {
		this.subject = subject;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
