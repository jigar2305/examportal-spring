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
	public Object getQuestions(Integer examId) throws Exception {
		Optional<ExamBean> exam = examRepo.findById(examId);
		if (exam.isEmpty()) {
			return new ResponseBean<>(examId, "data not found", 404);
		} else {
			List<ExamquestionBean> examquestion = examquestionRepo.findByExam(exam);
			return new ResponseBean<>(examquestion, "get examquestion successfully", 200);
		}
	}

	@Override
	public Object getExamQuestion(Integer examId) throws Exception {
		Optional<ExamBean> exam = examRepo.findById(examId);
		if (exam.isEmpty()) {
			return new ResponseBean<>(examId, "data not found", 404);
		} else {
			List<ExamquestionBean> examquestion = examquestionRepo.findByExam(exam);
			return new ResponseBean<>(examquestion, "get examquestion successfully", 200);
		}
	}

	@Override
	public Object checkanswer(CheckquestionanswerBean questions) throws Exception {
		ResultBean result = questionService.checkAnswer(questions);
		return new ResponseBean<>(result, result.getExam().getExamName() + " complated successfully", 200);
	}

	@Override
	public Object deleteExamQuestios(Integer examId) throws Exception {
		Optional<ExamBean> exam = examRepo.findById(examId);
		if (exam.isEmpty()) {
			return new ResponseBean<>(examId, "Exam not found", 404);
		} else {
			examquestionRepo.deleteAllByExam(exam);
			return new ResponseBean<>(examId, "Questions deleted successfully", 200);
		}
	}

	@Override
	public Object getExamQuestionWithImage(Integer examId) throws Exception {
		Optional<ExamBean> exam = examRepo.findById(examId);
		if (exam.isEmpty()) {
			return new ResponseBean<>(examId, "data not found", 404);
		} else {
			List<ExamquestionBean> examquestion = examquestionRepo.findByExam(exam);
			for (int i = 0; i < examquestion.size(); i++) {
				examquestion.get(i).setCorrectAnswer(null);
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
					} catch (IOException e) {
					}
				}
			}
			return new ResponseBean<>(examquestion, "get question successfully", 200);
		}
	}

}
