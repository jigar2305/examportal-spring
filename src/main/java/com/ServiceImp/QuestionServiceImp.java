package com.ServiceImp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Bean.CheckquestionanswerBean;
import com.Bean.ExamMSubjectBean;
import com.Bean.ImageBean;
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
import com.Service.CommonService;
import com.Service.QuestionService;
import com.Service.SubjectFileService;

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

	@Autowired
	CommonService examquestionService;

	@Autowired
	SubjectFileService fileService;

	@Override
	public List<ExamquestionBean> addRendomQuestionByManySubject(ExamMSubjectBean addquestion) throws Exception {
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
						question.add(examquestionService.setExamQue(quehard.get(randomIndex), exam));
						quehard.remove(randomIndex);
					}
				}
			} else if (level.equalsIgnoreCase("easy")) {
				if (easy >= number) {
					for (int j = 0; j < number; j++) {
						int randomIndex = rand.nextInt(queeasy.size());
						question.add(examquestionService.setExamQue(queeasy.get(randomIndex), exam));
						queeasy.remove(randomIndex);
					}
				}
			} else if (level.equalsIgnoreCase("easy-moderate")) {
				if (number % 2 == 0) {
					if (easy / 2 >= number / 2) {
						for (int j = 0; j < number / 2; j++) {
							int randomIndex = rand.nextInt(queeasy.size());
							question.add(examquestionService.setExamQue(queeasy.get(randomIndex), exam));
							queeasy.remove(randomIndex);
						}
					}
					if (moderate / 2 >= number / 2) {
						for (int j = 0; j < number / 2; j++) {
							int randomIndex = rand.nextInt(quemoderate.size());
							question.add(examquestionService.setExamQue(quemoderate.get(randomIndex), exam));
							quemoderate.remove(randomIndex);
						}
					}
				} else {
					if (easy / 2 >= (number + 1) / 2) {
						for (int j = 0; j < (number + 1) / 2; j++) {
							int randomIndex = rand.nextInt(queeasy.size());
							question.add(examquestionService.setExamQue(queeasy.get(randomIndex), exam));
							queeasy.remove(randomIndex);
						}
					}
					if (moderate / 2 >= (number - 1) / 2) {
						for (int j = 0; j < (number - 1) / 2; j++) {
							int randomIndex = rand.nextInt(quemoderate.size());
							question.add(examquestionService.setExamQue(quemoderate.get(randomIndex), exam));
							quemoderate.remove(randomIndex);
						}
					}
				}
			} else if (level.equalsIgnoreCase("moderate-hard")) {
				if (number % 2 == 0) {
					if (hard / 2 > number / 2) {
						for (int j = 0; j < number / 2; j++) {
							int randomIndex = rand.nextInt(quehard.size());
							question.add(examquestionService.setExamQue(quehard.get(randomIndex), exam));
							quehard.remove(randomIndex);
						}
					}
					if (moderate / 2 >= number / 2) {
						for (int j = 0; j < number / 2; j++) {
							int randomIndex = rand.nextInt(quemoderate.size());
							question.add(examquestionService.setExamQue(quemoderate.get(randomIndex), exam));
							quemoderate.remove(randomIndex);
						}
					}
				} else {
					if (hard / 2 >= (number + 1) / 2) {
						for (int j = 0; j < (number + 1) / 2; j++) {
							int randomIndex = rand.nextInt(quehard.size());
							question.add(examquestionService.setExamQue(quehard.get(randomIndex), exam));
							quehard.remove(randomIndex);
						}
					}
					if (moderate / 2 >= (number - 1) / 2) {
						for (int j = 0; j < (number - 1) / 2; j++) {
							int randomIndex = rand.nextInt(quemoderate.size());
							question.add(examquestionService.setExamQue(quemoderate.get(randomIndex), exam));
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
	public ResultBean checkAnswer(CheckquestionanswerBean questions) throws Exception {
		List<QuestionanswerBean> que = questions.getQuestions();
		ExamBean exam = examRepo.findByExamId(questions.getExam().getExamId());
		UserBean user = userRepo.findByEmail(questions.getEmail());
		List<UserquestionanswerBean> uqa = new ArrayList<>();
		Integer total = que.size();
		Integer obtain = 0;
		for (QuestionanswerBean i : que) {
			UserquestionanswerBean q = new UserquestionanswerBean();
			ExamquestionBean queq = examquestionRepo.findByExamquestionId(i.getExamquestionId());
			q.setUser(user);
			q.setQuestion(queq);
			q.setExam(exam);
			q.setSelectedOption(i.getSelected());
			uqa.add(q);
			if (queq.getCorrectAnswer().equalsIgnoreCase(i.getSelected())) {
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
				if (!(subjectBean == null && questionBean.getQuestion().isEmpty() && questionBean.getA().isEmpty()
						&& questionBean.getB().isEmpty() && questionBean.getC().isEmpty()
						&& questionBean.getD().isEmpty() && questionBean.getCorrectAnswer().isEmpty())
						&& (questionBean.getCorrectAnswer().equalsIgnoreCase("a")
								|| questionBean.getCorrectAnswer().equalsIgnoreCase("b")
								|| questionBean.getCorrectAnswer().equalsIgnoreCase("c")
								|| questionBean.getCorrectAnswer().equalsIgnoreCase("d")
								|| questionBean.getLevel().equalsIgnoreCase("easy")
								|| questionBean.getLevel().equalsIgnoreCase("moderate")
								|| questionBean.getLevel().equalsIgnoreCase("hard"))
						&& questionRepo.findByQuestion(questionBean.getQuestion()) == null
						&& questionBean.getCorrectAnswer().length() == 1) {
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
	public Object addQuestions(List<QuestionBean> questions) throws Exception {
		try {
//			for (int i = 0; i < questions.size(); i++) {
//				if (questionRepo.findByQuestion(questions.get(i).getQuestion()) == null) {
//					if (questions.get(i).getUrl() != null) {
//
//					}
//					questionRepo.save(questions.get(i));
//				}
//			}
			return new ResponseBean<>(questions, "Questions added successfully", 200);
		} catch (Exception e) {
			return new ResponseBean<>(e, TECHNICAL_ERROR, 500);
		}
	}

	@Override
	public Object getAllQuestions() throws Exception {
		List<QuestionBean> questions = (List<QuestionBean>) questionRepo.findAll();
		return new ResponseBean<>(questions, "get Questions successfully", 200);
	}

	@Override
	public Object checkForDelete(Integer questionId) throws Exception {
		QuestionBean question = questionRepo.findByQuestionId(questionId);
		if (question == null) {
			return new ResponseBean<>(questionId, "Question Not Found", 404);
		} else {
			try {
//				List<ExamquestionBean> examquestionBean = examquestionRepo.findByQuestion(question);
				ResponseBean<Integer> res = new ResponseBean<>();
//				res.setData(examquestionBean.size());
				res.setMsg("get successfully");
				res.setApicode(200);
				return res;
			} catch (Exception e) {
				return new ResponseBean<>(e, TECHNICAL_ERROR, 500);
			}
		}
	}

	@Override
	public Object deleteQuestion(Integer questionId) throws Exception {
		Optional<QuestionBean> questionBean = questionRepo.findById(questionId);
		if (questionBean.isEmpty()) {
			return new ResponseBean<>(questionId, "question not found", 404);
		} else {
			try {
				questionRepo.deleteById(questionId);
				return new ResponseBean<>(questionId, "deleted successfully", 200);
			} catch (Exception e) {
				return new ResponseBean<>(e, TECHNICAL_ERROR, 500);
			}
		}
	}

	@Override
	public Object getQuestionById(Integer questionId) throws Exception {
		Optional<QuestionBean> questionBean = questionRepo.findById(questionId);
		if (questionBean.isEmpty()) {
			return new ResponseBean<>(questionId, "Question Not Found", 404);
		} else {
			return new ResponseBean<>(questionBean, "Get Question Successfully", 200);
		}
	}

	@Override
	public Object updateQuestion(QuestionBean question) throws Exception {
		try {
			QuestionBean que = questionRepo.save(question);
			return new ResponseBean<>(que, "Question Updated Successfully", 200);
		} catch (Exception e) {
			return new ResponseBean<>(e, TECHNICAL_ERROR, 500);
		}
	}

	@Override
	public Object sendQuestion(Integer number) {
		List<QuestionBean> que = (List<QuestionBean>) questionRepo.findAll();
		if (que.size() > number) {
			List<QuestionBean> question = new ArrayList<>();
			for (int i = 0; i < number; i++) {
				int randomIndex = rand.nextInt(que.size());
				question.add(que.get(randomIndex));
				que.remove(randomIndex);
			}
			return new ResponseBean<>(question, "updated successfully", 200);
		} else {
			return new ResponseBean<>(number, "out of question please add question first", 401);
		}
	}

	@Override
	public Object fileRead(MultipartFile excel) throws Exception {
		List<QuestionBean> questions;
		try {
			questions = addquestion(excel);
			return new ResponseBean<>(questions, "question added successfully", 200);
		} catch (Exception e) {
			return new ResponseBean<>(e, TECHNICAL_ERROR, 500);
		}
	}

	@Override
	public Object addQuestion(QuestionBean question) throws Exception {
		try {
			if (questionRepo.findByQuestion(question.getQuestion()) == null) {
				if (question.getUrl() != null && !question.getUrl().isEmpty()) {
					String url = question.getUrl();
					question.setUrl(null);
					QuestionBean s = questionRepo.save(question);
					s.setUrl(url);
					QuestionBean Q = saveImage(s);
					if (Q != null) {
						questionRepo.save(Q);
					}
				} else {
					questionRepo.save(question);
				}
			}
			return new ResponseBean<>(question, "Question added successfully", 200);
		} catch (Exception e) {
			return new ResponseBean<>(e, TECHNICAL_ERROR, 500);
		}
	}

	public QuestionBean saveImage(QuestionBean question) throws Exception {
		String mainpath = "D:\\Exam-Portal-Spring\\src\\main\\resources\\questionImage";
		File folder = new File(mainpath, question.getSubject().getSubjectName() + "");
		folder.mkdir();

		String name = question.getQuestionId() + ".";
		String base64String = question.getUrl();
		String[] strings = base64String.split(",");
		String extension;
		switch (strings[0]) {
		case "data:image/jpeg;base64":
			extension = "jpeg";
			break;
		case "data:image/png;base64":
			extension = "png";
			break;
		default:
			extension = "jpg";
			break;
		}
		byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
		File file = new File(folder, name + extension);
		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			outputStream.write(data);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		question.setUrl("src/main/resources/questionImage/" + question.getSubject().getSubjectName() + "/"
				+ question.getQuestionId() + "." + extension);
		return question;
	}

	@Override
	public Object getQuestionImage(Integer questionId) throws Exception {
		QuestionBean question = questionRepo.findByQuestionId(questionId);
		ImageBean imageBean = new ImageBean();
		try {
			if (question.getUrl().endsWith("jpeg")) {
				imageBean.setImagetype("data:image/jpeg;base64,");
			} else if (question.getUrl().endsWith("png")) {
				imageBean.setImagetype("data:image/png;base64,");
			} else {
				imageBean.setImagetype("data:image/jpg;base64,");
			}
			byte[] image = fileService.getImage(question.getUrl());
			if (image == null) {
				return new ResponseBean<>(imageBean, "file not fond", 500);
			} else {
				imageBean.setImage(image);
				imageBean.setQuestionId(questionId);
				return new ResponseBean<>(imageBean, "image get sussessfully", 200);
			}

		} catch (IOException e) {
			return new ResponseBean<>(e, "file not fond", 500);
		}
	}

}
