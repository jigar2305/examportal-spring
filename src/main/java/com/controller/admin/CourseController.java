package com.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.forms.CourseBean;
import com.bean.forms.SubjectBean;
import com.repository.CourseRepository;
import com.repository.SubjectRepository;

@CrossOrigin
@RequestMapping("/course")
@RestController
public class CourseController {

	@Autowired
	CourseRepository courseRepo;

	@Autowired
	SubjectRepository subjectRepo;

	@Autowired
	SubjectController subcontroller;

	@PostMapping("/add")
	public ResponseEntity<?> addcourse(@RequestBody CourseBean course) {
		CourseBean courses = courseRepo.findByCourseName(course.getCourseName());
		ResponseBean<CourseBean> res = new ResponseBean<>();
		if (courses == null) {
			courseRepo.save(course);
			res.setData(course);
			res.setMsg("course added..");
			return ResponseEntity.ok(res);
		} else {
			res.setData(course);
			res.setMsg("course exist..");
			return ResponseEntity.ok(res);
		}
	}

	@GetMapping("/list")
	public ResponseEntity<?> listcourses() {
		List<CourseBean> courses = (List<CourseBean>) courseRepo.findAll();
		ResponseBean<List<CourseBean>> res = new ResponseBean<>();
		res.setData(courses);
		res.setMsg("list successfully");
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/delete/{courseId}")
	public ResponseEntity<?> deletecourse(@PathVariable("courseId") Integer courseId) {
		Optional<CourseBean> courseBean = courseRepo.findById(courseId);
		ResponseBean<Object> res = new ResponseBean<>();
		if (courseBean.isEmpty()) {
			res.setData(courseId);
			res.setMsg("not found");
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(res);
		} else {
			List<SubjectBean> sub = subjectRepo.findByCourse(courseBean);
			for (int i = 0; i < sub.size(); i++) {
				subcontroller.deletesubject(sub.get(i).getSubjectId());
			}
			courseRepo.deleteById(courseId);
			res.setData(courseId);
			res.setMsg("deleted successfully");
			return ResponseEntity.ok(res);
		}
	}

	@GetMapping("/coursebyId/{courseId}")
	public ResponseEntity<?> getcoursebyid(@PathVariable("courseId") Integer courseId) {
		Optional<CourseBean> course = courseRepo.findById(courseId);
		ResponseBean<Optional<CourseBean>> res = new ResponseBean<>();
		if (course.isEmpty()) {
			res.setData(course);
			res.setMsg("course not exist.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			res.setData(course);
			res.setMsg("get course successfully");
			return ResponseEntity.ok(res);
		}
	}
	
	@GetMapping("/child/{courseId}")
	public ResponseEntity<?> getsubjectincourse(@PathVariable("courseId") Integer courseId) {
		Optional<CourseBean> course = courseRepo.findById(courseId);
		if (course.isEmpty()) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(courseId);
			res.setMsg("course not exist.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			ResponseBean<List<SubjectBean>> res = new ResponseBean<>();
			List<SubjectBean> subject = subjectRepo.findByCourse(course);
			res.setData(subject);
			res.setMsg("get subject successfully");
			return ResponseEntity.ok(res);
		}
	}
	

}
