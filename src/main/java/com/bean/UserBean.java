package com.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bean.forms.ExamBean;
import com.bean.forms.ResultBean;
import com.bean.forms.SubjectBean;
import com.bean.forms.UserquestionanswerBean;
import com.fasterxml.jackson.annotation.JsonIgnore;




@Table(name = "users")
@Entity
public class UserBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer userId;
	
	private String firstName;

	private String lastName;
	
	private String email;
	
	private String password;

	private String gender;
	
	private Boolean active;
	
	private Integer otp;
	
	private String authToken;
	
	@ManyToOne()
	@JoinColumn(name = "roleId",nullable = false)
	private RoleBean role;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<ResultBean> results;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<UserquestionanswerBean> userquestionanswers; 
	
	@JsonIgnore
	@ManyToMany(mappedBy = "users")
    Set<ExamBean> exams;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "users")
    Set<SubjectBean> subjects;

	public Set<SubjectBean> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<SubjectBean> subjects) {
		this.subjects = subjects;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public RoleBean getRole() {
		return role;
	}

	public void setRole(RoleBean role) {
		this.role = role;
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

	public Set<ExamBean> getExams() {
		return exams;
	}

	public void setExams(Set<ExamBean> exams) {
		this.exams = exams;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	
}
