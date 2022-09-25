package com.bean.forms;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "Examquestion")
public class ExamquestionBean {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer examquestionId;

	
	@ManyToOne
	@JoinColumn(name = "questionId")
	private QuestionBean question;

	
	@ManyToOne
	@JoinColumn(name = "examId")
	private ExamBean exam;


	public Integer getExamquestionId() {
		return examquestionId;
	}


	public void setExamquestionId(Integer examquestionId) {
		this.examquestionId = examquestionId;
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
	

}
