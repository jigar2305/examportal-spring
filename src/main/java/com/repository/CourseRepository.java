package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.forms.CourseBean;

@Repository
public interface CourseRepository extends CrudRepository<CourseBean, Integer>{
	CourseBean findByCourseName(String courseName);

}
