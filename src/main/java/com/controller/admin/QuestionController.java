package com.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.forms.ExamquestionBean;
import com.bean.forms.QuestionBean;
import com.repository.ExamquestionRepository;
import com.repository.QuestionRepository;


@CrossOrigin
@RequestMapping("/que")
@RestController
public class QuestionController {

	@Autowired
	QuestionRepository questionRepo;
	
	@Autowired
	ExamquestionRepository examquestionRepo;

	@PostMapping("/add")
	public ResponseEntity<?> addquestion(@RequestBody List<QuestionBean> questions) {
		for (QuestionBean questionBean : questions) {
		System.out.println(	questionBean.getLevel());
		}
		QuestionBean d = null;
		for (int i = 0; i < questions.size(); i++) {
			if (questionRepo.findByQuestion(questions.get(i).getQuestion()) == null) {
				d = questionRepo.save(questions.get(i));
			}
		}
		ResponseBean<List<QuestionBean>> res = new ResponseBean<>();
		if (d == null) {
			res.setData(questions);
			res.setMsg("not add please try again..");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(res);
		} else {
			res.setData(questions);
			res.setMsg("added successfully..");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
		}
	}

	@GetMapping("/list")
	public ResponseEntity<?> listquestion() {
		List<QuestionBean> questions = (List<QuestionBean>) questionRepo.findAll();
		ResponseBean<List<QuestionBean>> res = new ResponseBean<>();
		res.setData(questions);
		res.setMsg("list successfully");
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/child/{questionId}")
	public ResponseEntity<?> checkfordelete(@PathVariable("questionId") Integer questionId) {
		QuestionBean  question = questionRepo.findByQuestionId(questionId);
		if(question == null) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(questionId);
			res.setMsg("not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}else {
			List<ExamquestionBean> examquestionBean = examquestionRepo.findByQuestion(question);
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(examquestionBean.size());
			res.setMsg("successfully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@DeleteMapping("/delete/{questionId}")
	public ResponseEntity<?> deletequestion(@PathVariable("questionId") Integer questionId) {
		Optional<QuestionBean> questionBean = questionRepo.findById(questionId);
		ResponseBean<Object> res = new ResponseBean<>();
		if (questionBean.isEmpty()) {
			res.setData(questionId);
			res.setMsg("question not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			questionRepo.deleteById(questionId);
			res.setData(questionId);
			res.setMsg("deleted successfully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@GetMapping("/get/{questionId}")
	public ResponseEntity<?> getquestionbyId(@PathVariable("questionId") Integer questionId) {
		Optional<QuestionBean> questionBean = questionRepo.findById(questionId);
		if (questionBean.isEmpty()) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(questionId);
			res.setMsg("question not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			ResponseBean<Optional<QuestionBean>> res = new ResponseBean<>();
			res.setData(questionBean);
			res.setMsg("fetch successfully");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updatequestionbyId(@RequestBody QuestionBean question) {
		QuestionBean que = questionRepo.save(question);
		ResponseBean<QuestionBean> res = new ResponseBean<>();
		if (que == null) {
			res.setData(question);
			res.setMsg("something went wrong..");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			res.setData(que);
			res.setMsg("updated successfully..");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
		}
	}

	@GetMapping("/question/{number}")
	public ResponseEntity<?> sendquestion(@PathVariable("number") Integer number) {
		List<QuestionBean> que = (List<QuestionBean>) questionRepo.findAll();
		if(que.size() >number){			
			List<QuestionBean> question = new ArrayList<QuestionBean>();
			Random rand = new Random();
			for (int i = 0; i < number; i++) {
				int randomIndex = rand.nextInt(que.size());
				question.add(que.get(randomIndex));
				que.remove(randomIndex);
			}
			ResponseBean<List<QuestionBean>> res = new ResponseBean<>();
			res.setData(question);
			res.setMsg("updated successfully..");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
		}else {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(number);
			res.setMsg("out of question please add question first");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
	}
	


}
