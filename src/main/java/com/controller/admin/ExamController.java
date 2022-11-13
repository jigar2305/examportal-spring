package com.controller.admin;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Transient;

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

import com.bean.ExamMSubjectBean;
import com.bean.ResponseBean;
import com.bean.UserBean;
import com.bean.examstatusBean;
import com.bean.forms.ExamBean;
import com.bean.forms.ExamquestionBean;
import com.bean.forms.ResultBean;
import com.repository.ExamRepository;
import com.repository.ExamquestionRepository;
import com.repository.ResultRepository;
import com.repository.UserRepository;
import com.service.QuestionService;

@CrossOrigin
@RestController
@RequestMapping("/exam")
public class ExamController {
	@Autowired
	ExamRepository examRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ResultRepository resultRepo;

	@Autowired
	QuestionService questionService;

	@Autowired
	ExamquestionRepository examquestionRepo;

	@Transient
	@PostMapping("/add")
	public ResponseEntity<?> addexamandquestion(@RequestBody ExamMSubjectBean examsubject) {
		ExamBean examBean = examRepo.findByExamName(examsubject.getExamName());
		ResponseBean<ExamBean> res = new ResponseBean<>();
		ExamBean exam = new ExamBean();
		if (examBean == null) {
			exam.setExamName(examsubject.getExamName());
			exam.setIsshow(examsubject.getIsshow());
			exam.setLevel(examsubject.getLevel());
			exam.setTime(examsubject.getTime() * 60);
			exam.setDate(examsubject.getDate());
			exam.setPercentage(examsubject.getPercentage());
			LocalTime startAt = LocalTime.parse(examsubject.getStartAt());
			exam.setStartAt(startAt);
			LocalTime endAt = LocalTime.parse(examsubject.getEndAt());
			exam.setEndAt(endAt);
			ExamBean exam1 = examRepo.save(exam);
			if (exam1 != null) {
				try {
					List<ExamquestionBean> equestions = questionService
							.randomquestionbymultiplesubjectbylevel(examsubject);
					if (equestions.size() > 0) {
						res.setData(exam);
						res.setMsg("exam added sussessfully...");
						return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
					} else {
						res.setData(null);
						examRepo.delete(exam1);
						res.setMsg("please add questions first");
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ExamBean examBean2 = examRepo.findByExamId(exam1.getExamId());
					if (examBean2 != null) {
						examRepo.deleteById(examBean2.getExamId());
						res.setData(exam);
						res.setMsg("Technical error occourd...");
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
					} else {
						res.setData(exam);
						res.setMsg("Technical error occourd...");
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
					}
				}
			} else {
				res.setData(exam);
				res.setMsg("Technical error occourd...");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
			}
		} else {
			res.setData(examBean);
			res.setMsg("exam alredy added...");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
	}

	@GetMapping("/list")
	public ResponseEntity<?> list() {
		List<ExamBean> exams = (List<ExamBean>) examRepo.findAll();
		ResponseBean<List<ExamBean>> res = new ResponseBean<>();
		res.setData(exams);
		res.setMsg("sussessfully");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
	}

	@GetMapping("/list/{userId}")
	public ResponseEntity<?> getexambyuserId(@PathVariable("userId") Integer userId) {
		UserBean user = userRepo.findByUserId(userId);
		List<ExamBean> exams = (List<ExamBean>) examRepo.findByUsers(user);
		for (int i = 0; i < exams.size(); i++) {
			if (resultRepo.findByExamAndUser(exams.get(i), user) != null) {
				exams.remove(i);
			}
		}
		for (ExamBean examBean : exams) {
			System.out.println(examBean.getDate());
		}
		ResponseBean<List<ExamBean>> res = new ResponseBean<>();
		res.setData(exams);
		res.setMsg("sussessfully");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
	}

	@DeleteMapping("/delete/{examId}")
	public ResponseEntity<?> deleteexam(@PathVariable("examId") Integer examId) {
		ExamBean examBean = examRepo.findByExamId(examId);
		ResponseBean<Object> res = new ResponseBean<>();
		if (examBean == null) {
			res.setData(examId);
			res.setMsg("exam not found");
			return ResponseEntity.ok(res);
		} else {
			examBean.getUsers().clear();
			examRepo.save(examBean);
			List<ExamquestionBean> equestion = examquestionRepo.findByExam(examBean);
			if (equestion != null) {
				examquestionRepo.deleteAll(equestion);
			}
			List<ResultBean> results = resultRepo.findByExam(examBean);
			if (results != null && results.size() > 0) {
				resultRepo.deleteAll(results);
			}
			examRepo.delete(examBean);
			res.setData(examId);
			res.setMsg("deleted successfully");
			return ResponseEntity.ok(res);
		}
	}

	@GetMapping("/child/{examId}")
	public ResponseEntity<?> isenroll(@PathVariable("examId") Integer examId) {
		ExamBean examBean = examRepo.findByExamId(examId);
		if (examBean == null) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(examId);
			res.setMsg("exam not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(examBean.getUsers().size());
			res.setMsg("fetch successfully");
			return ResponseEntity.ok(res);
		}
	}

	@GetMapping("/get/{examId}")
	public ResponseEntity<?> getquestionbyId(@PathVariable("examId") Integer examId) {
		Optional<ExamBean> examBean = examRepo.findById(examId);
		if (examBean.isEmpty()) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(examId);
			res.setMsg("exam not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			ResponseBean<Optional<ExamBean>> res = new ResponseBean<>();
			res.setData(examBean);
			res.setMsg("fetch successfully");
			return ResponseEntity.ok(res);
		}
	}

	@GetMapping("/statusofexam/{examId}")
	public ResponseEntity<?> getstatusofexam(@PathVariable("examId") Integer examId) {
		ExamBean examBean = examRepo.findByExamId(examId);
		if (examBean == null) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(examId);
			res.setMsg("exam not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			ResponseBean<List<examstatusBean>> res = new ResponseBean<>();
			List<examstatusBean> examstatusBeans = new ArrayList<>();
			List<UserBean> users = userRepo.findByExams(examBean);
			for (UserBean userBean : users) {
				examstatusBean examstatusBean = new examstatusBean();
				ResultBean resultBean = resultRepo.findByExamAndUser(examBean, userBean);
				examstatusBean.setUser(userBean);
				if (resultBean == null) {
					examstatusBean.setStatus("Not Complated");
				} else {
					examstatusBean.setResult(resultBean);
					examstatusBean.setStatus("Complated");
				}
				examstatusBeans.add(examstatusBean);
			}
			res.setData(examstatusBeans);
			res.setMsg("fetch successfully");
			return ResponseEntity.ok(res);
		}
	}

}
