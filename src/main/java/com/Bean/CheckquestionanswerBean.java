package com.Bean;

import java.util.List;

import com.Entity.ExamBean;


public class CheckquestionanswerBean {
	
	String email;
	List<QuestionanswerBean> questions;
	ExamBean exam;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<QuestionanswerBean> getQuestions() {
		return questions;
	}
	public void setQuestions(List<QuestionanswerBean> questions) {
		this.questions = questions;
	}
	public ExamBean getExam() {
		return exam;
	}
	public void setExam(ExamBean exam) {
		this.exam = exam;
	}
	
}
