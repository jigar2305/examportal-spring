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
	
	@JsonIgnore
	@OneToMany(mappedBy = "question")
	Set<UserquestionanswerBean> userquestionanswers;

}
