package com.ServiceImp;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bean.ResponseBean;
import com.Entity.ExamBean;
import com.Entity.ResultBean;
import com.Entity.UserBean;
import com.Entity.UserquestionanswerBean;
import com.Repositoy.ExamRepository;
import com.Repositoy.ResultRepository;
import com.Repositoy.UserRepository;
import com.Repositoy.UserquestionanswerRepository;
import com.Service.ResultService;

@Service
public class ResultServiceImp implements ResultService {

	private static final String SUCCESS = "get Result Sussessfully";

	@Autowired
	UserRepository userRepo;

	@Autowired
	ResultRepository resultRepo;

	@Autowired
	UserquestionanswerRepository userquestionanswerRepo;

	@Autowired
	ExamRepository examRepo;

	@Autowired
	EntityManager entityManager;

	@Override
	public Object getResultsById(Integer userId) throws Exception {
		UserBean user = userRepo.findByUserId(userId);
		if (user == null) {
			return new ResponseBean<>(userId, "User Not Found", 404);
		} else {
			List<ResultBean> results = resultRepo.findByUser(user);
			return new ResponseBean<>(results, SUCCESS, 200);
		}
	}

	@Override
	public Object getResultById(Integer resultId) throws Exception {
		ResultBean result = resultRepo.findByResultId(resultId);
		if (result == null) {
			return new ResponseBean<>(resultId, "Result not found", 404);
		} else {
			return new ResponseBean<>(result, SUCCESS, 200);
		}
	}

	@Override
	public Object getAllQuestionByExamIdAndUserId(Integer userId, Integer examId) throws Exception {
		List<Optional<UserquestionanswerBean>> userquestionanswerBean = userquestionanswerRepo.findByExamAndUser(examId,
				userId);
		if (!userquestionanswerBean.isEmpty()) {
			if (!userquestionanswerBean.get(0).get().getExam().getIsshow()) {
				for (int i = 0; i < userquestionanswerBean.size(); i++) {
					userquestionanswerBean.get(i).get().getQuestion().setCorrectAnswer(null);
				}
			}
		}
		return new ResponseBean<>(userquestionanswerBean, "get Question Successfully", 200);
	}

	@Override
	public Object getAllResultByExamId(Integer examId) throws Exception {
		ExamBean exam = examRepo.findByExamId(examId);
		if (exam == null) {
			return new ResponseBean<>(examId, "Exam Not Found", 404);
		} else {
			List<ResultBean> results = resultRepo.findByExam(exam);
			return new ResponseBean<>(results, SUCCESS, 200);
		}
	}

}
