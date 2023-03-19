package com.Service;

import org.springframework.stereotype.Service;

import com.Entity.CourseBean;

@Service
public interface CourseService {

	Object addCourse(CourseBean course) throws Exception;

	Object listCourses() throws Exception;

	Object deleteCourse(Integer courseId) throws Exception;

	Object findCourseById(Integer courseId) throws Exception;

	Object checkForDelete(Integer courseId) throws Exception;

}
