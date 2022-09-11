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
@Table(name = "exam")
public class ExamBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer examId;
	
	private String examName;
	
	
	@ManyToOne
	@JoinColumn(name = "subjectId",nullable = false)
	private SubjectBean subject;
	
	@JsonIgnore
	@OneToMany(mappedBy = "exam")	
	private Set<ExamquestionBean> examquestions;
	
	@JsonIgnore
	@OneToMany(mappedBy = "exam")
	Set<ResultBean> results;
	
	@JsonIgnore
	@OneToMany(mappedBy = "exam")
	Set<UserquestionanswerBean> Userquestionanswers;
	

	
	

}
