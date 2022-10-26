package com.bean.forms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SubjectFile")
public class SubjectFileBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer subjectfileId;
	
	@ManyToOne()
	@JoinColumn(name = "subjectId")
	private SubjectBean subject;
	
	private String fileName;
	
	@Column(columnDefinition="TEXT")
	private String url;
	
	@Column(columnDefinition="TEXT")
	private String fileString;
	
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
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


	public String getFileString() {
		return fileString;
	}


	public void setFileString(String fileString) {
		this.fileString = fileString;
	}




	
	

}
