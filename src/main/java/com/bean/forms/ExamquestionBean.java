package com.bean.forms;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Data;

@Data
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
	

}
