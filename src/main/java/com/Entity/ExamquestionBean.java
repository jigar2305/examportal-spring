package com.Entity;

import java.util.Set;

import javax.persistence.CascadeType;
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
@Table(name = "Examquestion")
public class ExamquestionBean {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer examquestionId;

	private String question;
	private String a;
	private String b;
	private String c;
	private String d;
	private String correctAnswer;
	
	private String level;
	
	@ManyToOne
	@JoinColumn(name = "examId")
	private ExamBean exam;
	
	@JsonIgnore
	@OneToMany(mappedBy = "question",cascade = {CascadeType.ALL})	
	private Set<UserquestionanswerBean> userquestionanswers;

	public Set<UserquestionanswerBean> getUserquestionanswers() {
		return userquestionanswers;
	}

	public void setUserquestionanswers(Set<UserquestionanswerBean> userquestionanswers) {
		this.userquestionanswers = userquestionanswers;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getExamquestionId() {
		return examquestionId;
	}

	public void setExamquestionId(Integer examquestionId) {
		this.examquestionId = examquestionId;
	}

	public ExamBean getExam() {
		return exam;
	}

	public void setExam(ExamBean exam) {
		this.exam = exam;
	}

}
