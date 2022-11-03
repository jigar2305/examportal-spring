package com.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.forms.Getsubjectfile;
import com.bean.forms.QuestionBean;
import com.bean.forms.SubjectBean;
import com.bean.forms.SubjectFileBean;
import com.repository.QuestionRepository;
import com.repository.SubjectFileRepository;
import com.repository.SubjectRepository;
import com.service.SubjectFileService;

@CrossOrigin
@RestController
@RequestMapping("/subject")
public class SubjectController {

	@Autowired
	SubjectRepository subjectRepo;

	@Autowired
	QuestionRepository questionRepo;

	@Autowired
	SubjectFileService subjectFileService;

	@Autowired
	SubjectFileRepository fileRepo;

	@PostMapping("/add")
	public ResponseEntity<?> addsubject2(@RequestBody Getsubjectfile subjectfile) throws IOException {
		SubjectBean subject = subjectfile.getSubject();
		List<SubjectFileBean> files = subjectfile.getFiles();

		SubjectBean subjectBean = subjectRepo.findBySubjectName(subject.getSubjectName());

		ResponseBean<SubjectBean> res = new ResponseBean<>();
		if (subjectBean == null) {

			SubjectBean subjectres = subjectRepo.save(subject);
			if (subjectres != null) {
				List<SubjectFileBean> rsfile = subjectFileService.addfiles(files, subjectres);
				if (rsfile == null) {
					subjectRepo.deleteById(subjectres.getSubjectId());
					res.setData(subjectBean);
					res.setMsg("something went wrong..");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
				} else {
					res.setData(subjectres);
					res.setMsg("subject added..");
					return ResponseEntity.ok(res);
				}
			}
			res.setData(subjectBean);
			res.setMsg("something went wrong..");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			res.setData(subjectBean);
			res.setMsg("subject exist..");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
	}

	@PostMapping("/update")
	public ResponseEntity<?> updatesubject(@RequestBody Getsubjectfile subjectfile) throws IOException {
		SubjectBean subject = subjectfile.getSubject();
		List<SubjectFileBean> files = subjectfile.getFiles();

		ResponseBean<SubjectBean> res = new ResponseBean<>();
		SubjectBean subjectres = subjectRepo.save(subject);
		if (subjectres != null) {
			List<SubjectFileBean> rsfile = subjectFileService.addfiles(files, subjectres);
			if (rsfile == null) {
				subjectRepo.deleteById(subjectres.getSubjectId());
				res.setData(subjectfile.getSubject());
				res.setMsg("something went wrong..");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
			} else {
				res.setData(subjectres);
				res.setMsg("subject added..");
				return ResponseEntity.ok(res);
			}
		} else {
			res.setData(subjectfile.getSubject());
			res.setMsg("something went wrong..");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}

	}

	@GetMapping("/list")
	public ResponseEntity<?> listsubject() {
		List<SubjectBean> subjects = (List<SubjectBean>) subjectRepo.findAll();
		ResponseBean<List<SubjectBean>> res = new ResponseBean<>();
		res.setData(subjects);
		res.setMsg("list successfully");
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/delete/{subjectId}")
	public ResponseEntity<?> deletesubject(@PathVariable("subjectId") Integer subjectId) {
		SubjectBean subject = subjectRepo.findBySubjectId(subjectId);
		ResponseBean<Object> res = new ResponseBean<>();
		if (subject == null) {
			res.setData(subjectId);
			res.setMsg("subjects not found ");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			subject.getUsers().clear();
			subjectRepo.save(subject);
			List<QuestionBean> questions = questionRepo.findBySubject(subject);
			if (questions != null) {
				questionRepo.deleteAll(questions);
			}
			subjectRepo.delete(subject);
			res.setData(subjectId);
			res.setMsg("deleted successfully");
			return ResponseEntity.ok(res);
		}
	}

	@GetMapping("/get/{subjectId}")
	public ResponseEntity<?> getsubjectbyid(@PathVariable("subjectId") Integer subjectId) {
		Optional<SubjectBean> subject = subjectRepo.findById(subjectId);
		if (subject.isEmpty()) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(subjectId);
			res.setMsg("subject not exist.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			ResponseBean<Optional<SubjectBean>> res = new ResponseBean<>();
			res.setData(subject);
			res.setMsg("get subject successfully");
			return ResponseEntity.ok(res);
		}
	}

	@GetMapping("/getfiles/{subjectId}")
	public ResponseEntity<?> getfiles(@PathVariable("subjectId") Integer subjectId) {
		Optional<SubjectBean> subject = subjectRepo.findById(subjectId);
		if (subject.isEmpty()) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(subjectId);
			res.setMsg("subject not exist.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			ResponseBean<List<SubjectFileBean>> res = new ResponseBean<>();
			List<SubjectFileBean> subjectfile = fileRepo.findBySubject(subject);
			for (SubjectFileBean subjectFileBean : subjectfile) {
				subjectFileBean.setFileString(null);
				subjectFileBean.setUrl(null);
			}
			res.setData(subjectfile);
			res.setMsg("get subject file successfully");
			return ResponseEntity.ok(res);
		}
	}

	@DeleteMapping("/deletefile/{subjectfileId}")
	public ResponseEntity<?> deletesubjectfile(@PathVariable("subjectfileId") Integer subjectfileId) {
		SubjectFileBean fileBean = fileRepo.getReferenceById(subjectfileId);
		ResponseBean<Object> res = new ResponseBean<>();
		if (fileBean == null) {
			res.setData(subjectfileId);
			res.setMsg("file not found ");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			fileRepo.delete(fileBean);
			res.setData(subjectfileId);
			res.setMsg("deleted successfully");
			return ResponseEntity.ok(res);
		}
	}
	
	@GetMapping("/child/{subjectId}")
	public ResponseEntity<?> getquebysubject(@PathVariable("subjectId") Integer subjectId) {
		Optional<SubjectBean> subject = subjectRepo.findById(subjectId);
		if (subject.isEmpty()) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(subjectId);
			res.setMsg("subject not exist.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			ResponseBean<Integer> res = new ResponseBean<>();
			List<QuestionBean> questionBeans = questionRepo.findBySubject(subject);
			res.setData(questionBeans.size());
			res.setMsg("get question successfully");
			return ResponseEntity.ok(res);
		}
	}

}
