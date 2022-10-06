package com.bean.forms;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;


@Entity
@Table(name = "SubjectFile")
public class SubjectFileBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer subjectfileId;
	
	@ManyToOne()
	@JoinColumn(name = "subjectId")
	private SubjectBean subject;
	
	private String url;

	public SubjectBean getSubject() {
		return subject;
	}

	public void setSubject(SubjectBean subject) {
		this.subject = subject;
	}

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
	
	
	
	

}
