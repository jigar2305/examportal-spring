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

import lombok.Data;

@Data
@Entity
@Table(name = "questions")
public class QuestionBean {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer questionId;
	
	private String question;
	private String a;
	private String b;
	private String c;
	private String d;
	private String correctAnswer;

	@ManyToOne
	@JoinColumn(name = "subjectId")
	private SubjectBean subject;
	
	@JsonIgnore
	@OneToMany(mappedBy = "question")	
	private Set<ExamquestionBean> examquestions;
	
	@JsonIgnore
	@OneToMany(mappedBy = "question")	
	private Set<UserquestionanswerBean> userquestionanswers;

}
