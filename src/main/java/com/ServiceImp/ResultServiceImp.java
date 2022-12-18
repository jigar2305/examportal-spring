package com.ServiceImp;

import java.util.List;
import java.util.Optional;

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

	@Override
	public ResponseBean<?> getResultsById(Integer userId) {
		UserBean user = userRepo.findByUserId(userId);
		if (user == null) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(userId);
			res.setMsg("User Not Found");
			res.setApicode(404);
			return res;
		} else {
			List<ResultBean> results = resultRepo.findByUser(user);
			ResponseBean<List<ResultBean>> res = new ResponseBean<>();
			res.setData(results);
			res.setMsg(SUCCESS);
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public ResponseBean<?> getResultById(Integer resultId) {
		ResultBean result = resultRepo.findByResultId(resultId);
		if (result == null) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(resultId);
			res.setMsg("Result not found");
			res.setApicode(404);
			return res;
		} else {
			ResponseBean<ResultBean> res = new ResponseBean<>();
			res.setData(result);
			res.setMsg(SUCCESS);
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public ResponseBean<?> getAllQuestionByExamIdAndUserId(Integer userId, Integer examId) {
		List<Optional<UserquestionanswerBean>> userquestionanswerBean = userquestionanswerRepo.findByExamAndUser(examId, userId);
		ResponseBean<List<Optional<UserquestionanswerBean>>> res = new ResponseBean<>();
		res.setData(userquestionanswerBean);
		res.setMsg("get Question Successfully");
		res.setApicode(200);
		return res;
	}

	@Override
	public ResponseBean<?> getAllResultByExamId(Integer examId) {
		ExamBean exam = examRepo.findByExamId(examId);
		if (exam == null) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(examId);
			res.setMsg("Exam Not Found");
			res.setApicode(404);
			return res;
		} else {
			List<ResultBean> results = resultRepo.findByExam(exam);
			ResponseBean<List<ResultBean>> res = new ResponseBean<>();
			res.setData(results);
			res.setMsg(SUCCESS);
			res.setApicode(200);
			return res;
		}
	}

}
