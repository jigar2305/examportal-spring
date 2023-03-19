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
	public Object addExamAndQuestion(ExamMSubjectBean examsubject) throws Exception {
		ExamBean examBean = examRepo.findByExamName(examsubject.getExamName());
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
					return new ResponseBean<>(exam, "exam added sussessfully", 200);
				} else {
					examRepo.delete(exam1);
					return new ResponseBean<>("please add questions first", 300);
				}
			} catch (Exception e) {
				ExamBean examBean2 = examRepo.findByExamId(exam1.getExamId());
				if (examBean2 != null) {
					examRepo.deleteById(examBean2.getExamId());
				}
				return new ResponseBean<>(exam, TECHNICAL_ERROR, 500);
			}
		} else {
			return new ResponseBean<>(examBean, "exam alredy added", 403);
		}
	}

	@Override
	public Object listExams() throws Exception {
		List<ExamBean> exams = examRepo.findAll();
		return new ResponseBean<>(exams, "get Exams Sussessfully", 200);
	}

	@Override
	public Object getExamByUserId(Integer userId) throws Exception {
		UserBean user = userRepo.findByUserId(userId);
		List<ExamBean> exam = examRepo.findByUsers(user);
		List<ExamBean> exams = new ArrayList<>();
		for (int i = 0; i < exam.size(); i++) {
			if (resultRepo.findByExamAndUser(exam.get(i), user) == null) {
				exams.add(exam.get(i));
			}
		}
		return new ResponseBean<>(exams, "get Exam Sussessfully", 200);
	}

	@Override
	public Object deleteExam(Integer examId) throws Exception {
		ExamBean examBean = examRepo.findByExamId(examId);
		if (examBean == null) {
			return new ResponseBean<>(examId, NOT_FOUND, 404);
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
			return new ResponseBean<>(examId, examBean.getExamName() + " deleted successfully", 200);
		}
	}

	@Override
	public Object isEnroll(Integer examId) throws Exception {
		ExamBean examBean = examRepo.findByExamId(examId);
		if (examBean == null) {
			return new ResponseBean<>(examId, NOT_FOUND, 404);
		} else {
			return new ResponseBean<>(examBean.getUsers().size(), FETCH_DATA, 200);
		}
	}

	@Override
	public Object getQuestionById(Integer examId) throws Exception {
		Optional<ExamBean> examBean = examRepo.findById(examId);
		if (examBean.isEmpty()) {
			return new ResponseBean<>(examId, NOT_FOUND, 404);
		} else {
			return new ResponseBean<>(examBean, FETCH_DATA, 200);
		}
	}

	@Override
	public Object getStatusOfExam(Integer examId) throws Exception {
		ExamBean examBean = examRepo.findByExamId(examId);
		if (examBean == null) {
			return new ResponseBean<>(examId, NOT_FOUND, 404);
		} else {
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
			return new ResponseBean<>(examstatusBeans, FETCH_DATA, 200);
		}
	}

	@Override
	public Object enrollExam(EnroleexamBean enroleexam) throws Exception {
		ExamBean exam = examRepo.findByExamId(enroleexam.getExamId());
		ExamBean exam2 = new ExamBean();
		try {
			if (exam != null) {
				for (int i = 0; i < enroleexam.getUserId().size(); i++) {
					UserBean user = userRepo.findByUserId(enroleexam.getUserId().get(i));
					if (user != null) {
						exam.getUsers().add(user);
						exam2 = examRepo.save(exam);
					}
				}
			}
			return new ResponseBean<>(exam2, "exam send to user", 200);
		} catch (Exception e) {
			return new ResponseBean<>(e, NOT_FOUND, 404);
		}
	}

}
