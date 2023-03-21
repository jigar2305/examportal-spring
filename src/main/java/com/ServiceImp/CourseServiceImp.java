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
	public Object addCourse(CourseBean course) throws Exception {
		CourseBean courses = courseRepo.findByCourseName(course.getCourseName());
		if (courses == null) {
			CourseBean courseBean = courseRepo.save(course);
			return new ResponseBean(courseBean, "course added successfully", 200);
		} else {
			return new ResponseBean(courses, "course exist", 403);
		}
	}

	@Override
	public Object listCourses() throws Exception {
		List<CourseBean> courses = (List<CourseBean>) courseRepo.findAll();
		return new ResponseBean(courses, "list successfully", 200);
	}

	@Override
	public Object deleteCourse(Integer courseId) throws Exception {
		Optional<CourseBean> courseBean = courseRepo.findById(courseId);
		try {
			if (courseBean.isEmpty()) {
				return new ResponseBean(courseId, "Course Not Found", 404);
			} else {
				List<SubjectBean> sub = subjectRepo.findByCourse(courseBean);
				for (int i = 0; i < sub.size(); i++) {
					subjectService.deleteSubject(sub.get(i).getSubjectId());
				}
				courseRepo.deleteById(courseId);
				return new ResponseBean(courseId, "Course deleted successfully", 200);
			}
		} catch (Exception e) {
			return new ResponseBean(courseId, "Technical error occurred", 500);
		}
	}

	@Override
	public Object findCourseById(Integer courseId) throws Exception {
		Optional<CourseBean> course = courseRepo.findById(courseId);
		if (course.isEmpty()) {
			return new ResponseBean(null, "Course Not Found", 404);
		} else {
			return new ResponseBean(course, "get course successfully", 200);
		}
	}

	@Override
	public Object checkForDelete(Integer courseId) throws Exception {
		Optional<CourseBean> course = courseRepo.findById(courseId);
		if (course.isEmpty()) {
			return new ResponseBean(courseId, "course not exist.");

		} else {
			List<SubjectBean> subject = subjectRepo.findByCourse(course);
			return new ResponseBean(subject, "get subject successfully");

		}
	}

}
