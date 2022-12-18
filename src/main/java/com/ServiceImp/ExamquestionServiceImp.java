package com.ServiceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bean.CheckquestionanswerBean;
import com.Bean.ResponseBean;
import com.Entity.ExamBean;
import com.Entity.ExamquestionBean;
import com.Entity.ResultBean;
import com.Repositoy.ExamRepository;
import com.Repositoy.ExamquestionRepository;
import com.Service.ExamquestionService;
import com.Service.QuestionService;

@Service
public class ExamquestionServiceImp implements ExamquestionService {
	
	@Autowired
	ExamRepository examRepo;
	
	@Autowired
	ExamquestionRepository examquestionRepo;
	
	@Autowired
	QuestionService questionService;

	@Override
	public ResponseBean<?> getQuestions(Integer examId) {
		Optional<ExamBean> exam = examRepo.findById(examId);
		if (exam.isEmpty()) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(examId);
			res.setMsg("data not found");
			res.setApicode(404);
			return res;

		} else {
			ResponseBean<List<ExamquestionBean>> res = new ResponseBean<>();
			List<ExamquestionBean> examquestion = examquestionRepo.findByExam(exam);
			res.setData(examquestion);
			res.setMsg("get examquestion successfully");
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public ResponseBean<?> getExamQuestion(Integer examId) {
		Optional<ExamBean> exam = examRepo.findById(examId);
		if (exam.isEmpty()) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(examId);
			res.setMsg("data not found");
			res.setApicode(404);
			return res;

		} else {
			ResponseBean<List<ExamquestionBean>> res = new ResponseBean<>();
			List<ExamquestionBean> examquestion = examquestionRepo.findByExam(exam);
//			List<QuestionBean> questions = new ArrayList<>();
//			for (int i = 0; i < examquestion.size(); i++) {
//				questions.add(examquestion.get(i).getQuestion());
//			}
			res.setData(examquestion);
			res.setMsg("get question successfully");
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public ResponseBean<?> checkanswer(CheckquestionanswerBean questions) {
		ResultBean result = questionService.checkAnswer(questions);
		ResponseBean<ResultBean> res = new ResponseBean<>();
		res.setData(result);
		res.setMsg(result.getExam().getExamName()+" complated successfully");	
		res.setApicode(200);
		return res;
	}

	@Override
	public ResponseBean<?> deleteExamQuestios(Integer examId) {
		Optional<ExamBean> exam = examRepo.findById(examId);
		ResponseBean<Object> res = new ResponseBean<>();
		if (exam.isEmpty()) {
			res.setData(examId);
			res.setMsg("Exam not found");
			res.setApicode(404);
			return res;
		} else {
			examquestionRepo.deleteAllByExam(exam);
			res.setData(examId);
			res.setMsg("Questions deleted successfully");
			res.setApicode(200);
			return res;
		}
	}
	
	

}
