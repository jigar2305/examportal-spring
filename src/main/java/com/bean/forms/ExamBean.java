package com.bean.forms;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	private Boolean isshow;
	
	private String level;
	
	private String date;
	
	private LocalTime startAt;
	
	private LocalTime endAt;
	
	@JsonIgnore
	@OneToMany(mappedBy = "exam",cascade = {CascadeType.ALL})	
	private Set<ExamquestionBean> examquestions;
	
	@JsonIgnore
	@OneToMany(mappedBy = "exam",cascade = {CascadeType.ALL})
	Set<ResultBean> results;
	
	@JsonIgnore
	@OneToMany(mappedBy = "exam",cascade = {CascadeType.ALL})
	Set<UserquestionanswerBean> userquestionanswers;
	
	@JsonIgnore
	@ManyToMany()
	@JoinTable(
	  name = "enroleexam", 
	  joinColumns = @JoinColumn(name = "examId"), 
	  inverseJoinColumns = @JoinColumn(name = "userId"))
	Set<UserBean> users;

	public Boolean getIsshow() {
		return isshow;
	}


	public void setIsshow(Boolean isshow) {
		this.isshow = isshow;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


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


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public LocalTime getStartAt() {
		return startAt;
	}


	public void setStartAt(LocalTime startAt) {
		this.startAt = startAt;
	}


	public LocalTime getEndAt() {
		return endAt;
	}


	public void setEndAt(LocalTime endAt) {
		this.endAt = endAt;
	}





	

}
