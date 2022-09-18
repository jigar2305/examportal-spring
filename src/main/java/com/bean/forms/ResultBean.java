package com.bean.forms;

import javax.persistence.CascadeType;
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
@Table(name = "result")
public class ResultBean {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer resultId;
	
	private Integer obtainMarks;
	
	private Integer totalMarks;
	
	@ManyToOne(cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "userId")
	private UserBean user;
	
	@ManyToOne(cascade = {CascadeType.REMOVE})
	@JoinColumn(name = "examId")
	private ExamBean exam;

	
}
