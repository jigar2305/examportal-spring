package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.AddquestionBean;
import com.bean.UserBean;
import com.bean.forms.CheckquestionanswerBean;
import com.bean.forms.ExamBean;
import com.bean.forms.ExamquestionBean;
import com.bean.forms.QuestionBean;
import com.bean.forms.QuestionanswerBean;
import com.bean.forms.ResultBean;
import com.bean.forms.SubjectBean;
import com.bean.forms.UserquestionanswerBean;
import com.repository.ExamRepository;
import com.repository.QuestionRepository;
import com.repository.ResultRepository;
import com.repository.SubjectRepository;
import com.repository.UserRepository;
import com.repository.UserquestionanswerRepository;

@Service
public class QuestionService {
	@Autowired
	QuestionRepository questionRepo;

	@Autowired
	SubjectRepository subjectRepo;

	@Autowired
	ExamRepository examRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ResultRepository resultRepo;

	@Autowired
	UserquestionanswerRepository userquestionanswerRepo;

	public List<ExamquestionBean> randomquestion(AddquestionBean addquestion) {

		Optional<SubjectBean> subject = subjectRepo.findById(addquestion.getExam().getSubject().getSubjectId());
		List<QuestionBean> que = (List<QuestionBean>) questionRepo.findBySubject(subject);
		if (que.size() > addquestion.getNumber()) {
			List<ExamquestionBean> question = new ArrayList<ExamquestionBean>();
			Random rand = new Random();
			for (int i = 0; i < addquestion.getNumber(); i++) {
				int randomIndex = rand.nextInt(que.size());
				ExamquestionBean eq = new ExamquestionBean();
				eq.setExam(addquestion.getExam());
				eq.setQuestion(que.get(randomIndex));
				question.add(eq);
				que.remove(randomIndex);
			}
			return question;
		} else {
			return null;
		}
	}

	public ResultBean checkanswer(CheckquestionanswerBean questions) {
		List<QuestionanswerBean> que = questions.getQuestions();
		ExamBean exam = examRepo.findByExamId(questions.getExam().getExamId());
		UserBean user = userRepo.findByEmail(questions.getEmail());
		System.out.println(user.getUserId());
		List<UserquestionanswerBean> uqa = new ArrayList<>();
		user.setPassword(null);
		Integer total = que.size();
		Integer obtain = 0;
		for (QuestionanswerBean i : que) {
			UserquestionanswerBean q = new UserquestionanswerBean();
			QuestionBean queq = questionRepo.findByQuestionId(i.getQuestionId());
			q.setUser(user);
			q.setQuestion(queq);
			q.setExam(exam);
			System.out.println(i.getSelected());
			q.setSelectedOption(i.getSelected());
			uqa.add(q);
			if (i.getCorrectAnswer() == i.getSelected()) {
				obtain = obtain + 1;
			}
		}
		try {
			userquestionanswerRepo.saveAll(uqa);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("in error");
		}
		ResultBean result = new ResultBean();
		result.setObtainMarks(obtain);
		result.setTotalMarks(total);
		System.out.println(result.getTotalMarks());
		result.setExam(exam);
		result.setUser(user);
		resultRepo.save(result);

		return result;
	}

}
