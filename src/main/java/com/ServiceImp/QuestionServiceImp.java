package com.ServiceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Bean.CheckquestionanswerBean;
import com.Bean.ExamMSubjectBean;
import com.Bean.QuestionanswerBean;
import com.Bean.ResponseBean;
import com.Entity.ExamBean;
import com.Entity.ExamquestionBean;
import com.Entity.QuestionBean;
import com.Entity.ResultBean;
import com.Entity.SubjectBean;
import com.Entity.UserBean;
import com.Entity.UserquestionanswerBean;
import com.Repositoy.ExamRepository;
import com.Repositoy.ExamquestionRepository;
import com.Repositoy.QuestionRepository;
import com.Repositoy.ResultRepository;
import com.Repositoy.SubjectRepository;
import com.Repositoy.UserRepository;
import com.Repositoy.UserquestionanswerRepository;
import com.Service.QuestionService;

@Service
public class QuestionServiceImp implements QuestionService {

	private static final Random rand = new Random();
	private static final String TECHNICAL_ERROR = "Technical error occurred";

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

	@Override
	public List<ExamquestionBean> addRendomQuestionByManySubject(ExamMSubjectBean addquestion) {
		ExamBean exam = examRepo.findByExamName(addquestion.getExamName());
		Integer total = 0;
		List<ExamquestionBean> question = new ArrayList<>();
		for (int i = 0; i < addquestion.getSubjects().size(); i++) {
			SubjectBean subjectBean = subjectRepo.findBySubjectName(addquestion.getSubjects().get(i).getSubjectName());
			List<QuestionBean> quehard = questionRepo.findBySubjectAndLevel(subjectBean, "hard");
			List<QuestionBean> quemoderate = questionRepo.findBySubjectAndLevel(subjectBean, "moderate");
			List<QuestionBean> queeasy = questionRepo.findBySubjectAndLevel(subjectBean, "easy");
			Integer hard = quehard.size();
			Integer moderate = quemoderate.size();
			Integer easy = queeasy.size();
			Integer number = addquestion.getSubjects().get(i).getNumber();
			total = total + number;
			String level = exam.getLevel();

			if (level.equalsIgnoreCase("hard")) {
				if (hard >= number) {
					for (int j = 0; j < number; j++) {
						int randomIndex = rand.nextInt(quehard.size());
						ExamquestionBean eq = new ExamquestionBean();
						eq.setExam(exam);
						eq.setQuestion(quehard.get(randomIndex));
						question.add(eq);
						quehard.remove(randomIndex);
					}
				}
			} else if (level.equalsIgnoreCase("easy")) {
				if (easy >= number) {
					for (int j = 0; j < number; j++) {
						int randomIndex = rand.nextInt(queeasy.size());
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
							int randomIndex = rand.nextInt(queeasy.size());
							QuestionBean questionBean = queeasy.get(randomIndex);
							ExamquestionBean eq = new ExamquestionBean();
							eq.setExam(exam);
							eq.setQuestion(questionBean);
							question.add(eq);
							queeasy.remove(randomIndex);
						}
					}
					if (moderate / 2 > number / 2) {
						for (int j = 0; j < number / 2; j++) {
							int randomIndex = rand.nextInt(quemoderate.size());
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
							int randomIndex = rand.nextInt(queeasy.size());
							ExamquestionBean eq = new ExamquestionBean();
							eq.setExam(exam);
							eq.setQuestion(queeasy.get(randomIndex));
							question.add(eq);
							queeasy.remove(randomIndex);
						}
					}
					if (moderate / 2 > (number - 1) / 2) {
						for (int j = 0; j < (number - 1) / 2; j++) {
							int randomIndex = rand.nextInt(quemoderate.size());
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
							int randomIndex = rand.nextInt(quehard.size());
							ExamquestionBean eq = new ExamquestionBean();
							eq.setExam(exam);
							eq.setQuestion(quehard.get(randomIndex));
							question.add(eq);
							quehard.remove(randomIndex);
						}
					}
					if (moderate / 2 > number / 2) {
						for (int j = 0; j < number / 2; j++) {
							int randomIndex = rand.nextInt(quemoderate.size());
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
							int randomIndex = rand.nextInt(quehard.size());
							ExamquestionBean eq = new ExamquestionBean();
							eq.setExam(exam);
							eq.setQuestion(quehard.get(randomIndex));
							question.add(eq);
							quehard.remove(randomIndex);
						}
					}
					if (moderate / 2 > (number - 1) / 2) {
						for (int j = 0; j < (number - 1) / 2; j++) {
							int randomIndex = rand.nextInt(quemoderate.size());
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
			question.clear();
			return question;
		}

	}

	@Override
	public ResultBean checkAnswer(CheckquestionanswerBean questions) {
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
			if (i.getCorrectAnswer().equalsIgnoreCase(i.getSelected())) {
				obtain = obtain + 1;
			}
		}
		try {
			userquestionanswerRepo.saveAll(uqa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResultBean result = new ResultBean();
		result.setObtainMarks(obtain);
		result.setTotalMarks(total);
		Float percentage = exam.getPercentage();
		Float fi = total * percentage / 100;
		if (fi < obtain) {
			result.setStatus("Pass");
		} else {
			result.setStatus("Fail");
		}
		result.setExam(exam);
		result.setUser(user);
		ResultBean checkresult = resultRepo.findByExamAndUser(exam, user);
		if (checkresult == null) {
			resultRepo.save(result);
		}
		return result;
	}

	@Override
	public List<QuestionBean> addquestion(MultipartFile excel) throws IOException, NullPointerException {
		List<QuestionBean> questions = new ArrayList<>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = sheet.getRow(i);
				QuestionBean questionBean = new QuestionBean();
				questionBean.setQuestion(row.getCell(0).toString());
				questionBean.setA(row.getCell(1).toString());
				questionBean.setB(row.getCell(2).toString());
				questionBean.setC(row.getCell(3).toString());
				questionBean.setD(row.getCell(4).toString());
				questionBean.setCorrectAnswer(row.getCell(5).toString().toLowerCase());
				questionBean.setLevel(row.getCell(6).toString().toLowerCase());
				SubjectBean subjectBean = null;
				if (row.getCell(7).toString() != null) {
					String subject = row.getCell(7).toString();
					subjectBean = subjectRepo.findBySubjectName(subject);
				}
				if (!(subjectBean == null || questionBean.getQuestion().isEmpty() || questionBean.getA().isEmpty()
						|| questionBean.getB().isEmpty() || questionBean.getC().isEmpty()
						|| questionBean.getD().isEmpty() || questionBean.getCorrectAnswer().isEmpty()
						|| !questionBean.getCorrectAnswer().equalsIgnoreCase("a")
						|| !questionBean.getCorrectAnswer().equalsIgnoreCase("b")
						|| !questionBean.getCorrectAnswer().equalsIgnoreCase("c")
						|| !questionBean.getCorrectAnswer().equalsIgnoreCase("d")
						|| questionBean.getCorrectAnswer().length() != 1
						|| !questionBean.getLevel().equalsIgnoreCase("easy")
						|| !questionBean.getLevel().equalsIgnoreCase("moderate")
						|| !questionBean.getLevel().equalsIgnoreCase("hard"))
						&& questionRepo.findByQuestion(questionBean.getQuestion()) == null) {
					questionBean.setSubject(subjectBean);
					questions.add(questionRepo.save(questionBean));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return questions;
	}

	@Override
	public ResponseBean<?> addQuestions(List<QuestionBean> questions) {
		ResponseBean<List<QuestionBean>> res = new ResponseBean<>();
		try {
			for (int i = 0; i < questions.size(); i++) {
				if (questionRepo.findByQuestion(questions.get(i).getQuestion()) == null) {
					questionRepo.save(questions.get(i));
				}
			}
			res.setData(questions);
			res.setApicode(200);
			res.setMsg("Questions added successfully..");
			return res;
		} catch (Exception e) {
			res.setData(questions);
			res.setMsg(TECHNICAL_ERROR);
			res.setApicode(500);
			return res;
		}
	}

	@Override
	public ResponseBean<?> getAllQuestions() {
		List<QuestionBean> questions = (List<QuestionBean>) questionRepo.findAll();
		ResponseBean<List<QuestionBean>> res = new ResponseBean<>();
		res.setData(questions);
		res.setMsg("get Questions successfully");
		res.setApicode(200);
		return res;
	}

	@Override
	public ResponseBean<?> checkForDelete(Integer questionId) {
		QuestionBean question = questionRepo.findByQuestionId(questionId);
		if (question == null) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(questionId);
			res.setApicode(404);
			res.setMsg("Question Not Found");
			return res;
		} else {
			try {
				List<ExamquestionBean> examquestionBean = examquestionRepo.findByQuestion(question);
				ResponseBean<Integer> res = new ResponseBean<>();
				res.setData(examquestionBean.size());
				res.setMsg("successfully");
				res.setApicode(200);
				return res;
			} catch (Exception e) {
				ResponseBean<Integer> res = new ResponseBean<>();
				res.setData(questionId);
				res.setMsg(TECHNICAL_ERROR);
				res.setApicode(500);
				return res;
			}
		}
	}

	@Override
	public ResponseBean<?> deleteQuestion(Integer questionId) {
		Optional<QuestionBean> questionBean = questionRepo.findById(questionId);
		ResponseBean<Integer> res = new ResponseBean<>();
		if (questionBean.isEmpty()) {
			res.setData(questionId);
			res.setMsg("question not found");
			res.setApicode(404);
			return res;
		} else {
			try {
				questionRepo.deleteById(questionId);
				res.setData(questionId);
				res.setApicode(200);
				res.setMsg("deleted successfully");
				return res;
			} catch (Exception e) {
				res.setData(questionId);
				res.setMsg("Technical error occoured");
				res.setApicode(500);
				return res;
			}
		}
	}

	@Override
	public ResponseBean<?> getQuestionById(Integer questionId) {
		Optional<QuestionBean> questionBean = questionRepo.findById(questionId);
		if (questionBean.isEmpty()) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(questionId);
			res.setMsg("Question Not Found");
			res.setApicode(404);
			return res;
		} else {
			ResponseBean<Optional<QuestionBean>> res = new ResponseBean<>();
			res.setData(questionBean);
			res.setMsg("Get Question Successfully");
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public ResponseBean<?> updateQuestion(QuestionBean question) {
		ResponseBean<QuestionBean> res = new ResponseBean<>();
		try {
			QuestionBean que = questionRepo.save(question);
			res.setData(que);
			res.setMsg("Question Updated Successfully..");
			res.setApicode(200);
			return res;

		} catch (Exception e) {
			res.setData(question);
			res.setMsg(TECHNICAL_ERROR);
			res.setApicode(500);
			return res;
		}
	}

	@Override
	public ResponseBean<?> sendQuestion(Integer number) {
		List<QuestionBean> que = (List<QuestionBean>) questionRepo.findAll();
		if (que.size() > number) {
			List<QuestionBean> question = new ArrayList<>();
			for (int i = 0; i < number; i++) {
				int randomIndex = rand.nextInt(que.size());
				question.add(que.get(randomIndex));
				que.remove(randomIndex);
			}
			ResponseBean<List<QuestionBean>> res = new ResponseBean<>();
			res.setData(question);
			res.setMsg("updated successfully..");
			res.setApicode(200);
			return res;
		} else {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(number);
			res.setMsg("out of question please add question first");
			res.setApicode(401);
			return res;
		}
	}

	@Override
	public ResponseBean<?> fileRead(MultipartFile excel) {
		List<QuestionBean> questions;
		try {
			questions = addquestion(excel);
			ResponseBean<List<QuestionBean>> res = new ResponseBean<>();
			res.setData(questions);
			res.setMsg("question added successfully..");
			res.setApicode(200);
			return res;
		} catch (Exception e) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(null);
			res.setMsg(TECHNICAL_ERROR);
			res.setApicode(500);
			return res;
		}
	}

}
