package com.Controller.AdminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Entity.CourseBean;
import com.Service.CourseService;

@RequestMapping("/course")
@RestController
public class CourseController {

	@Autowired
	CourseService courseService;

	@PostMapping("/add")
	public Object addcourse(@RequestBody CourseBean course) throws Exception {
		return ResponseEntity.ok(courseService.addCourse(course));
	}

	@GetMapping("/list")
	public Object listcourses() throws Exception {
		return ResponseEntity.ok(courseService.listCourses());
	}

	@DeleteMapping("/delete/{courseId}")
	public Object deletecourse(@PathVariable("courseId") Integer courseId) throws Exception {
		return ResponseEntity.ok(courseService.deleteCourse(courseId));
	}

	@GetMapping("/coursebyId/{courseId}")
	public Object getcoursebyid(@PathVariable("courseId") Integer courseId) throws Exception {
		return ResponseEntity.ok(courseService.findCourseById(courseId));
	}

	@GetMapping("/child/{courseId}")
	public Object getsubjectincourse(@PathVariable("courseId") Integer courseId) throws Exception {
		return ResponseEntity.ok(courseService.checkForDelete(courseId));
	}

}
