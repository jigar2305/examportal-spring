package com.bean.forms;

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

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "subject")
public class SubjectBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer subjectId;
	
	private String subjectName;
	
	@ManyToOne()
	@JoinColumn(name = "courseId")
	private CourseBean course;
	
	@JsonIgnore
	@OneToMany(mappedBy = "subject" )
	private Set<QuestionBean> questions;
	
	@JsonIgnore
	@OneToMany(mappedBy = "subject" )
	private Set<ExamBean> exams;
}
