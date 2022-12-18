package com.Bean;


public class QuestionanswerBean {


	private Integer questionId;
	private Integer examquestionId;
	
	private String question;
	private String a;
	private String b;
	private String c;
	private String d;
	private String correctAnswer;

	private String selected;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public Integer getExamquestionId() {
		return examquestionId;
	}

	public void setExamquestionId(Integer examquestionId) {
		this.examquestionId = examquestionId;
	}
	
	
	
}
