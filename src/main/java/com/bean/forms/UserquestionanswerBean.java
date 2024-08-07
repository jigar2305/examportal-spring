package com.bean.forms;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bean.UserBean;

@Entity
@Table(name = "userquestionanswer")
public class UserquestionanswerBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer userquestionanswerId;
	
	@ManyToOne
	@JoinColumn(name = "userId",nullable = false)
	private UserBean user;
	
	@ManyToOne
	@JoinColumn(name = "questionId",nullable = false)
	private QuestionBean question;
	
	@ManyToOne
	@JoinColumn(name = "examId",nullable = false)
	private ExamBean exam;
	
	private String selectedOption;

	public Integer getUserquestionanswerId() {
		return userquestionanswerId;
	}

	public void setUserquestionanswerId(Integer userquestionanswerId) {
		this.userquestionanswerId = userquestionanswerId;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public QuestionBean getQuestion() {
		return question;
	}

	public void setQuestion(QuestionBean question) {
		this.question = question;
	}

	public ExamBean getExam() {
		return exam;
	}

	public void setExam(ExamBean exam) {
		this.exam = exam;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}
	
	
}
