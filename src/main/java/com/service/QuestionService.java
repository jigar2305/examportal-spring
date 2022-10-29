package com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bean.ExamMSubjectBean;
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
import com.repository.ExamquestionRepository;
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
	ExamquestionRepository examquestionRepo;

	@Autowired
	ResultRepository resultRepo;

	@Autowired
	UserquestionanswerRepository userquestionanswerRepo;

	public ResultBean checkanswer(CheckquestionanswerBean questions) {
		List<QuestionanswerBean> que = questions.getQuestions();
		ExamBean exam = examRepo.findByExamId(questions.getExam().getExamId());
		UserBean user = userRepo.findByEmail(questions.getEmail());
		List<UserquestionanswerBean> uqa = new ArrayList<>();
		Integer total = que.size();
		Integer obtain = 0;
		for (QuestionanswerBean i : que) {
			UserquestionanswerBean q = new UserquestionanswerBean();
			QuestionBean queq = questionRepo.findByQuestionId(i.getQuestionId());
			q.setUser(user);
			q.setQuestion(queq);
			q.setExam(exam);
			q.setSelectedOption(i.getSelected());
			uqa.add(q);
			System.out.println(i.getCorrectAnswer());
			System.out.println(i.getSelected());
			if (i.getCorrectAnswer().equalsIgnoreCase(i.getSelected())) {
				obtain = obtain + 1;
			}
		}
		try {
			userquestionanswerRepo.saveAll(uqa);
		} catch (Exception e) {
			System.out.println(e);
		}
		ResultBean result = new ResultBean();
		result.setObtainMarks(obtain);
		result.setTotalMarks(total);
		float fi = total/3;
		if(fi < obtain) {
			result.setStatus("pass");
		}else {
			result.setStatus("fail");
		}
		result.setExam(exam);
		result.setUser(user);
		resultRepo.save(result);

		return result;
	}

	public List<QuestionBean> addquestion(MultipartFile excel)  throws Exception{

		List<QuestionBean> questions = new ArrayList<>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = sheet.getRow(i);
				QuestionBean questionBean = new QuestionBean();
				questionBean.setQuestion(row.getCell(0).toString());
				questionBean.setA(row.getCell(1).toString());
				questionBean.setB(row.getCell(2).toString());
				questionBean.setC(row.getCell(3).toString());
				questionBean.setD(row.getCell(4).toString());
				questionBean.setCorrectAnswer(row.getCell(5).toString());
				questionBean.setLevel(row.getCell(6).toString());
				SubjectBean subjectBean = null;
				if(row.getCell(7).toString() != null) {
					String subject = row.getCell(7).toString();
					subjectBean = subjectRepo.findBySubjectName(subject);
				}
				if (subjectBean == null || questionBean.getQuestion().isEmpty() || questionBean.getA().isEmpty()
						|| questionBean.getB().isEmpty() || questionBean.getC().isEmpty()
						|| questionBean.getD().isEmpty() || questionBean.getCorrectAnswer().isEmpty()

				) {
				} else {
					if (questionRepo.findByQuestion(questionBean.getQuestion()) == null) {
					questionBean.setSubject(subjectBean);
					questions.add(questionRepo.save(questionBean));
				}
			}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return questions;
	}


	public List<ExamquestionBean> randomquestionbymultiplesubjectbylevel(ExamMSubjectBean addquestion) {
		ExamBean exam = examRepo.findByExamName(addquestion.getExamName());
		Integer total = 0;
		Random rand = new Random();
		List<ExamquestionBean> question = new ArrayList<ExamquestionBean>();
		for (int i = 0; i < addquestion.getSubjects().size(); i++) {
			SubjectBean subjectBean = subjectRepo.findBySubjectName(addquestion.getSubjects().get(i).getSubjectName());
			List<QuestionBean> quehard = questionRepo.findBySubjectAndLevel(subjectBean, "hard");
			List<QuestionBean> quemoderate = questionRepo.findBySubjectAndLevel(subjectBean, "moderate");
			List<QuestionBean> queeasy = questionRepo.findBySubjectAndLevel(subjectBean, "hard");
			Integer hard = quehard.size();
			Integer moderate = quemoderate.size();
			Integer easy = queeasy.size();
			Integer number = addquestion.getSubjects().get(i).getNumber();
			total = total + number;
			String level = exam.getLevel();

			if (level.equalsIgnoreCase("hard")) {
				if (hard > number) {
					for (int j = 0; j < hard; j++) {
						int randomIndex = rand.nextInt(hard);
						ExamquestionBean eq = new ExamquestionBean();
						eq.setExam(exam);
						eq.setQuestion(quehard.get(randomIndex));
						question.add(eq);
						quehard.remove(randomIndex);
					}
				}
			} else if (level.equalsIgnoreCase("easy")) {
				if (easy > number) {
					for (int j = 0; j < number; j++) {
						int randomIndex = rand.nextInt(easy);
						ExamquestionBean eq = new ExamquestionBean();
						eq.setExam(exam);
						eq.setQuestion(queeasy.get(randomIndex));
						question.add(eq);
						queeasy.remove(randomIndex);
					}
				}
			} else if (level.equalsIgnoreCase("easy-moderate")) {
				if (number % 2 == 0) {
					if (easy / 2 > number / 2) {
						for (int j = 0; j < number / 2; j++) {
							int randomIndex = rand.nextInt(easy);
							ExamquestionBean eq = new ExamquestionBean();
							eq.setExam(exam);
							eq.setQuestion(queeasy.get(randomIndex));
							question.add(eq);
							queeasy.remove(randomIndex);
						}
					}
					if (moderate / 2 > number / 2) {
						for (int j = 0; j < number / 2; j++) {
							int randomIndex = rand.nextInt(moderate);
							ExamquestionBean eq = new ExamquestionBean();
							eq.setExam(exam);
							eq.setQuestion(quemoderate.get(randomIndex));
							question.add(eq);
							quemoderate.remove(randomIndex);
						}
					}
				} else {
					if (easy / 2 > (number + 1) / 2) {
						for (int j = 0; j < (number + 1) / 2; j++) {
							int randomIndex = rand.nextInt(easy);
							ExamquestionBean eq = new ExamquestionBean();
							eq.setExam(exam);
							eq.setQuestion(queeasy.get(randomIndex));
							question.add(eq);
							queeasy.remove(randomIndex);
						}
					}
					if (moderate / 2 > (number - 1) / 2) {
						for (int j = 0; j < (number - 1) / 2; j++) {
							int randomIndex = rand.nextInt(moderate);
							ExamquestionBean eq = new ExamquestionBean();
							eq.setExam(exam);
							eq.setQuestion(quemoderate.get(randomIndex));
							question.add(eq);
							quemoderate.remove(randomIndex);
						}
					}
				}
			} else if (level.equalsIgnoreCase("moderate-hard")) {
				if (number % 2 == 0) {
					if (hard / 2 > number / 2) {
						for (int j = 0; j < number / 2; j++) {
							int randomIndex = rand.nextInt(hard);
							ExamquestionBean eq = new ExamquestionBean();
							eq.setExam(exam);
							eq.setQuestion(quehard.get(randomIndex));
							question.add(eq);
							quehard.remove(randomIndex);
						}
					}
					if (moderate / 2 > number / 2) {
						for (int j = 0; j < number / 2; j++) {
							int randomIndex = rand.nextInt(moderate);
							ExamquestionBean eq = new ExamquestionBean();
							eq.setExam(exam);
							eq.setQuestion(quemoderate.get(randomIndex));
							question.add(eq);
							quemoderate.remove(randomIndex);
						}
					}
				} else {
					if (hard / 2 > (number + 1) / 2) {
						for (int j = 0; j < (number + 1) / 2; j++) {
							int randomIndex = rand.nextInt(hard);
							ExamquestionBean eq = new ExamquestionBean();
							eq.setExam(exam);
							eq.setQuestion(quehard.get(randomIndex));
							question.add(eq);
							quehard.remove(randomIndex);
						}
					}
					if (moderate / 2 > (number - 1) / 2) {
						for (int j = 0; j < (number - 1) / 2; j++) {
							int randomIndex = rand.nextInt(moderate);
							ExamquestionBean eq = new ExamquestionBean();
							eq.setExam(exam);
							eq.setQuestion(quemoderate.get(randomIndex));
							question.add(eq);
							quemoderate.remove(randomIndex);
						}
					}
				}
			}
		}
		if (total == question.size()) {
			examquestionRepo.saveAll(question);
			return question;
		} else {
			question.removeAll(question);
			return question;
		}
	}

}
