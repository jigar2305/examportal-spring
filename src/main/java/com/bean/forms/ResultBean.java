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


@Entity
@Table(name = "result")
public class ResultBean {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer resultId;
	
	private Integer obtainMarks;
	
	private Integer totalMarks;
	
	private String status;
	
	@ManyToOne()
	@JoinColumn(name = "userId")
	private UserBean user;
	
	@ManyToOne()
	@JoinColumn(name = "examId")
	private ExamBean exam;

	public Integer getResultId() {
		return resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public Integer getObtainMarks() {
		return obtainMarks;
	}

	public void setObtainMarks(Integer obtainMarks) {
		this.obtainMarks = obtainMarks;
	}

	public Integer getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public ExamBean getExam() {
		return exam;
	}

	public void setExam(ExamBean exam) {
		this.exam = exam;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
