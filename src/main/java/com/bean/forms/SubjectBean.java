package com.bean.forms;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "subject")
public class SubjectBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer subjectId;
	
	private String subjectName;
	
	@ManyToOne()
	@JoinColumn(name = "courseId")
	private CourseBean course;
	
	@JsonIgnore
	@OneToMany(mappedBy = "subject" )
	private Set<QuestionBean> questions;
	
	@JsonIgnore
	@OneToMany(mappedBy = "subject")
	private Set<SubjectFileBean> subjectFiles;
	
	public Set<SubjectFileBean> getSubjectFiles() {
		return subjectFiles;
	}

	public void setSubjectFiles(Set<SubjectFileBean> subjectFiles) {
		this.subjectFiles = subjectFiles;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public CourseBean getCourse() {
		return course;
	}

	public void setCourse(CourseBean course) {
		this.course = course;
	}

	public Set<QuestionBean> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<QuestionBean> questions) {
		this.questions = questions;
	}


	
	
}
