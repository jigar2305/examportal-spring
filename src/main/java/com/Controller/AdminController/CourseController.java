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
	public ResponseEntity<?> addcourse(@RequestBody CourseBean course) {
		return ResponseEntity.ok(courseService.addCourse(course));
	}

	@GetMapping("/list")
	public ResponseEntity<?> listcourses() {
		return ResponseEntity.ok(courseService.listCourses());
	}

	@DeleteMapping("/delete/{courseId}")
	public ResponseEntity<?> deletecourse(@PathVariable("courseId") Integer courseId) {
		return ResponseEntity.ok(courseService.deleteCourse(courseId));
	}

	@GetMapping("/coursebyId/{courseId}")
	public ResponseEntity<?> getcoursebyid(@PathVariable("courseId") Integer courseId) {
		return ResponseEntity.ok(courseService.findCourseById(courseId));
	}

	@GetMapping("/child/{courseId}")
	public ResponseEntity<?> getsubjectincourse(@PathVariable("courseId") Integer courseId) {
		return ResponseEntity.ok(courseService.checkForDelete(courseId));
	}

}
