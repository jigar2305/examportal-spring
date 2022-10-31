package com.bean.forms;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "course")
public class CourseBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer courseId;
	
	private String courseName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "course",cascade = {CascadeType.ALL})
	List<SubjectBean> subjects;

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public List<SubjectBean> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectBean> subjects) {
		this.subjects = subjects;
	}
	

}
