package com.bean;

import java.util.List;

public class ExamMSubjectBean {
 
	private String examName;

	private Integer time;

	private Boolean isshow;

	private String level;
	
	private String date;
	
	private String startAt;
	
	private String endAt;
	
	private Float percentage;


	private List<SNNLBean> subjects;


	public String getExamName() {
		return examName;
	}


	public void setExamName(String examName) {
		this.examName = examName;
	}


	public Integer getTime() {
		return time;
	}


	public void setTime(Integer time) {
		this.time = time;
	}


	public Boolean getIsshow() {
		return isshow;
	}


	public void setIsshow(Boolean isshow) {
		this.isshow = isshow;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public List<SNNLBean> getSubjects() {
		return subjects;
	}


	public void setSubjects(List<SNNLBean> subjects) {
		this.subjects = subjects;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getStartAt() {
		return startAt;
	}


	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}


	public String getEndAt() {
		return endAt;
	}


	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}


	public Float getPercentage() {
		return percentage;
	}


	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}
	

}
