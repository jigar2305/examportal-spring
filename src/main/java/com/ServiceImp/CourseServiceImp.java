package com.ServiceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bean.ResponseBean;
import com.Entity.CourseBean;
import com.Entity.SubjectBean;
import com.Repositoy.CourseRepository;
import com.Repositoy.SubjectRepository;
import com.Service.CourseService;
import com.Service.SubjectService;

@Service
public class CourseServiceImp implements CourseService {

	@Autowired
	CourseRepository courseRepo;

	@Autowired
	SubjectRepository subjectRepo;

	@Autowired
	SubjectService subjectService;

	@Override
	public ResponseBean<?> addCourse(CourseBean course) {
		CourseBean courses = courseRepo.findByCourseName(course.getCourseName());
		ResponseBean<CourseBean> res = new ResponseBean<>();
		if (courses == null) {
			courseRepo.save(course);
			res.setData(course);
			res.setMsg("course added successfully..");
			res.setApicode(200);
			return res;
		} else {
			res.setData(courses);
			res.setMsg("course exist..");
			res.setApicode(403);
			return res;
		}
	}

	@Override
	public ResponseBean<?> listCourses() {
		List<CourseBean> courses = (List<CourseBean>) courseRepo.findAll();
		ResponseBean<List<CourseBean>> res = new ResponseBean<>();
		res.setData(courses);
		res.setMsg("list successfully");
		res.setApicode(200);
		return res;
	}

	@Override
	public ResponseBean<?> deleteCourse(Integer courseId) {
		Optional<CourseBean> courseBean = courseRepo.findById(courseId);
		ResponseBean<Integer> res = new ResponseBean<>();
		try {
			if (courseBean.isEmpty()) {
				res.setData(courseId);
				res.setMsg("Course Not Found");
				res.setApicode(404);
				return res;
			} else {
				List<SubjectBean> sub = subjectRepo.findByCourse(courseBean);
				for (int i = 0; i < sub.size(); i++) {
					subjectService.deleteSubject(sub.get(i).getSubjectId());
				}
				courseRepo.deleteById(courseId);
				res.setData(courseId);
				res.setMsg("Course deleted successfully");
				res.setApicode(200);
				return res;
			}
		} catch (Exception e) {
			res.setData(courseId);
			res.setMsg("Technical error occurred");
			res.setApicode(500);
			return res;
		}
	}

	@Override
	public ResponseBean<?> findCourseById(Integer courseId) {
		Optional<CourseBean> course = courseRepo.findById(courseId);
		ResponseBean<Optional<CourseBean>> res = new ResponseBean<>();
		if (course.isEmpty()) {
			res.setData(course);
			res.setMsg("Course Not Found");
			res.setApicode(404);
			return res;
		} else {
			res.setData(course);
			res.setMsg("get course successfully..");
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public ResponseBean<?> checkForDelete(Integer courseId) {
		Optional<CourseBean> course = courseRepo.findById(courseId);
		if (course.isEmpty()) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(courseId);
			res.setMsg("course not exist.");
			return res;
		} else {
			ResponseBean<List<SubjectBean>> res = new ResponseBean<>();
			List<SubjectBean> subject = subjectRepo.findByCourse(course);
			res.setData(subject);
			res.setMsg("get subject successfully");
			return res;
		}
	}

}
