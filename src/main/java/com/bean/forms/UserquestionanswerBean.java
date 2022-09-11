package com.bean.forms;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bean.UserBean;

import lombok.Data;

@Data
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
	@JoinColumn(name = "examId",nullable = false)
	private ExamBean exam;
	
	@ManyToOne
	@JoinColumn(name = "examquestionId",nullable = false)
	private ExamquestionBean question;
	
	private Integer selectedOption;
	
}
