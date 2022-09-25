package com.bean.forms;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bean.UserBean;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "exam")
public class ExamBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer examId;
	
	private String examName;
	
	private Integer time;
	
//	@ManyToOne
//	@JoinColumn(name = "subjectId",nullable = false)
//	private SubjectBean subject;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "exam")	
	private Set<ExamquestionBean> examquestions;
	
	@JsonIgnore
	@OneToMany(mappedBy = "exam")
	Set<ResultBean> results;
	
	@JsonIgnore
	@OneToMany(mappedBy = "exam")
	Set<UserquestionanswerBean> userquestionanswers;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
	  name = "enroleexam", 
	  joinColumns = @JoinColumn(name = "examId"), 
	  inverseJoinColumns = @JoinColumn(name = "userId"))
	Set<UserBean> users;




	public Integer getExamId() {
		return examId;
	}


	public void setExamId(Integer examId) {
		this.examId = examId;
	}


	public String getExamName() {
		return examName;
	}


	public void setExamName(String examName) {
		this.examName = examName;
	}


//	public SubjectBean getSubject() {
//		return subject;
//	}
//
//
//	public void setSubject(SubjectBean subject) {
//		this.subject = subject;
//	}


	public Set<ExamquestionBean> getExamquestions() {
		return examquestions;
	}


	public void setExamquestions(Set<ExamquestionBean> examquestions) {
		this.examquestions = examquestions;
	}


	public Set<ResultBean> getResults() {
		return results;
	}


	public void setResults(Set<ResultBean> results) {
		this.results = results;
	}


	public Set<UserquestionanswerBean> getUserquestionanswers() {
		return userquestionanswers;
	}


	public void setUserquestionanswers(Set<UserquestionanswerBean> userquestionanswers) {
		this.userquestionanswers = userquestionanswers;
	}


	public Set<UserBean> getUsers() {
		return users;
	}


	public void setUsers(Set<UserBean> users) {
		this.users = users;
	}


	public Integer getTime() {
		return time;
	}


	public void setTime(Integer time) {
		this.time = time;
	}



	

}
