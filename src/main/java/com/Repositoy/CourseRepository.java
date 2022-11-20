package com.Repositoy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Entity.CourseBean;


@Repository
public interface CourseRepository extends CrudRepository<CourseBean, Integer>{
	CourseBean findByCourseName(String courseName);

}
