package com.ServiceImp;

import java.io.IOException;
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
import com.Service.SubjectFileService;

@Service
public class ExamquestionServiceImp implements ExamquestionService {

	@Autowired
	ExamRepository examRepo;

	@Autowired
	ExamquestionRepository examquestionRepo;

	@Autowired
	QuestionService questionService;

	@Autowired
	SubjectFileService fileService;

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
		res.setMsg(result.getExam().getExamName() + " complated successfully");
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

	@Override
	public ResponseBean<?> getExamQuestionWithImage(Integer examId) {
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
			for (int i = 0; i < examquestion.size(); i++) {
				if (examquestion.get(i).getUrl() != null) {
					try {
						if (examquestion.get(i).getUrl().endsWith("jpeg")) {
							examquestion.get(i).setImagetype("data:image/jpeg;base64,");
						} else if (examquestion.get(i).getUrl().endsWith("png")) {
							examquestion.get(i).setImagetype("data:image/png;base64,");
						} else {
							examquestion.get(i).setImagetype("data:image/jpg;base64,");
						}
						byte[] image = fileService.getImage(examquestion.get(i).getUrl());
						examquestion.get(i).setImage(image);
					} catch (IOException e) {}
				}
			}
			res.setData(examquestion);
			res.setMsg("get question successfully");
			res.setApicode(200);
			return res;
		}
	}

}
