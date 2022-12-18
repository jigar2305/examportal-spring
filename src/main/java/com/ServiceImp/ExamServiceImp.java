package com.ServiceImp;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bean.EnroleexamBean;
import com.Bean.ExamMSubjectBean;
import com.Bean.ResponseBean;
import com.Bean.examstatusBean;
import com.Entity.ExamBean;
import com.Entity.ExamquestionBean;
import com.Entity.ResultBean;
import com.Entity.UserBean;
import com.Repositoy.CustomNativeRepository;
import com.Repositoy.ExamRepository;
import com.Repositoy.ExamquestionRepository;
import com.Repositoy.ResultRepository;
import com.Repositoy.UserRepository;
import com.Service.ExamService;
import com.Service.QuestionService;

@Service
public class ExamServiceImp implements ExamService {

	private static final String TECHNICAL_ERROR = "Technical error occurred";
	private static final String NOT_FOUND = "Exam Not Found";
	private static final String FETCH_DATA = "fetch successfully";

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
	
	@Autowired
	CustomNativeRepository customNativeRepo;

	@Transactional
	@Override
	public ResponseBean<?> addExamAndQuestion(ExamMSubjectBean examsubject) {
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
			try {
				List<ExamquestionBean> equestions = questionService.addRendomQuestionByManySubject(examsubject);
				if (!equestions.isEmpty()) {
					res.setData(exam);
					res.setApicode(200);
					res.setMsg("exam added sussessfully...");
					return res;
				} else {
					res.setData(null);
					examRepo.delete(exam1);
					res.setMsg("please add questions first");
					return res;
				}
			} catch (Exception e) {
				ExamBean examBean2 = examRepo.findByExamId(exam1.getExamId());
				if (examBean2 != null) {
					examRepo.deleteById(examBean2.getExamId());
					res.setData(exam);
					res.setApicode(500);
					res.setMsg(TECHNICAL_ERROR);
					return res;
				} else {
					res.setData(exam);
					res.setApicode(500);
					res.setMsg(TECHNICAL_ERROR);
					return res;
				}
			}
		} else {
			res.setData(examBean);
			res.setMsg("exam alredy added...");
			res.setApicode(403);
			return res;
		}
	}

	@Override
	public ResponseBean<?> listExams() {
		List<ExamBean> exams = examRepo.findAll();
		ResponseBean<List<ExamBean>> res = new ResponseBean<>();
		res.setData(exams);
		res.setMsg("get Exams Sussessfully");
		res.setApicode(200);
		return res;
	}

	@Override
	public ResponseBean<?> getExamByUserId(Integer userId) {
		UserBean user = userRepo.findByUserId(userId);
		List<ExamBean> exam = examRepo.findByUsers(user);
		List<ExamBean> exams = new ArrayList<>();
		for (int i = 0; i < exam.size(); i++) {
			if (resultRepo.findByExamAndUser(exam.get(i), user) == null) {
				exam.add(exam.get(i));
			}
		}
		ResponseBean<List<ExamBean>> res = new ResponseBean<>();
		res.setData(exams);
		res.setMsg("get Exam Successfully");
		res.setApicode(200);
		return res;
	}

	@Override
	public ResponseBean<?> deleteExam(Integer examId) {
		ExamBean examBean = examRepo.findByExamId(examId);
		ResponseBean<Object> res = new ResponseBean<>();
		if (examBean == null) {
			res.setData(examId);
			res.setMsg(NOT_FOUND);
			res.setApicode(404);
			return res;
		} else {
			customNativeRepo.deleteenroleexambyexam(examId);	
			List<ExamquestionBean> equestion = examquestionRepo.findByExam(examBean);
			if (equestion != null) {
				examquestionRepo.deleteAll(equestion);
			}
			List<ResultBean> results = resultRepo.findByExam(examBean);
			if (results != null && !results.isEmpty()) {
				resultRepo.deleteAll(results);
			}
			examRepo.deleteById(examId);
			res.setData(examId);
			res.setMsg(examBean.getExamName() + " deleted successfully");
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public ResponseBean<?> isEnroll(Integer examId) {
		ExamBean examBean = examRepo.findByExamId(examId);
		if (examBean == null) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(examId);
			res.setMsg(NOT_FOUND);
			res.setApicode(404);
			return res;
		} else {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(examBean.getUsers().size());
			res.setApicode(200);
			res.setMsg(FETCH_DATA);
			return res;
		}
	}

	@Override
	public ResponseBean<?> getQuestionById(Integer examId) {
		Optional<ExamBean> examBean = examRepo.findById(examId);
		if (examBean.isEmpty()) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(examId);
			res.setMsg(NOT_FOUND);
			res.setApicode(404);
			return res;
		} else {
			ResponseBean<Optional<ExamBean>> res = new ResponseBean<>();
			res.setData(examBean);
			res.setMsg(FETCH_DATA);
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public ResponseBean<?> getStatusOfExam(Integer examId) {
		ExamBean examBean = examRepo.findByExamId(examId);
		if (examBean == null) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(examId);
			res.setMsg(NOT_FOUND);
			res.setApicode(404);
			return res;
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
			res.setMsg(FETCH_DATA);
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public ResponseBean<?> enrollExam(EnroleexamBean enroleexam) {
		ExamBean exam = examRepo.findByExamId(enroleexam.getExamId());
		ExamBean exam2 = new ExamBean();
		if (exam != null) {
			for (int i = 0; i < enroleexam.getUserId().size(); i++) {
				UserBean user = userRepo.findByUserId(enroleexam.getUserId().get(i));
				if (user != null) {
					exam.getUsers().add(user);
					exam2 = examRepo.save(exam);
				}
			}
		}
		try {
			ResponseBean<ExamBean> res = new ResponseBean<>();
			res.setData(exam2);
			res.setMsg("exam send to user");
			res.setApicode(200);
			return res;
		} catch (Exception e) {
			ResponseBean<ExamBean> res = new ResponseBean<>();
			res.setData(exam);
			res.setMsg(TECHNICAL_ERROR);
			res.setApicode(404);
			return res;
		}
	}

}
