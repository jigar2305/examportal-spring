package com.Service;

import org.springframework.stereotype.Service;

import com.Bean.ResponseBean;
import com.Entity.CourseBean;

@Service
public interface CourseService {
	
     ResponseBean<?> addCourse(CourseBean course);
     
     ResponseBean<?> listCourses();
     
     ResponseBean<?> deleteCourse(Integer courseId);
     
     ResponseBean<?> findCourseById(Integer courseId);
     
     ResponseBean<?> checkForDelete(Integer courseId);

}
