package com.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.UserBean;
import com.bean.forms.EnroleSubjectFilesBean;
import com.bean.forms.SubjectBean;
import com.bean.forms.SubjectFileBean;
import com.repository.SubjectFileRepository;
import com.repository.SubjectRepository;
import com.repository.UserRepository;

@RestController
@RequestMapping("/subjectfile")
public class SubjectFileController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	SubjectRepository subjectRepo;

	@Autowired
	SubjectFileRepository subjectFileRepo;

	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody EnroleSubjectFilesBean filesBean) {
		for (int i = 0; i < filesBean.getSubjectId().size(); i++) {
			SubjectBean subject = subjectRepo.findBySubjectId(filesBean.getSubjectId().get(i));
			if (subject != null) {
				for (int j = 0; j < filesBean.getUserId().size(); j++) {
					UserBean user = userRepo.findByUserId(filesBean.getUserId().get(j));
					if (user != null) {
						subject.getUsers().add(user);
						subjectRepo.save(subject);
					}
				}
			}
		}
		ResponseBean<Boolean> res = new ResponseBean<>();
		res.setData(true);
		res.setMsg("files successfully send to users");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
	}

	@GetMapping("/get/{userId}")
	public ResponseEntity<?> getallfileforuser(@PathVariable("userId") Integer userId) {
		UserBean user = userRepo.findByUserId(userId);
		List<SubjectBean> subjects = subjectRepo.findByUsers(user);
		List<SubjectFileBean> files = new ArrayList<>();
		for (int i = 0; i < subjects.size(); i++) {
			if (subjectFileRepo.findBySubject(subjects.get(i)) != null) {
				files.addAll(subjectFileRepo.findBySubject(subjects.get(i)));
			}
		}
		for (SubjectFileBean subjectFileBean : files) {
			subjectFileBean.setFileString(null);
			subjectFileBean.setUrl(null);
		}
		ResponseBean<List<SubjectFileBean>> res = new ResponseBean<>();
		res.setData(files);
		res.setMsg("files get successfully");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
	}


	@GetMapping("/getfile/{subjectfileId}")
	public ResponseEntity<?> getfile(@PathVariable("subjectfileId") Integer subjectfileId)
			throws IOException {
		SubjectFileBean file = subjectFileRepo.findBySubjectfileId(subjectfileId);
		if (file == null) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(subjectfileId);
			res.setMsg("file not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			ResponseBean<SubjectFileBean> res = new ResponseBean<>();
			byte[] pdf = Files.readAllBytes(new File(file.getUrl()).toPath());
			String base64EncodedImageBytes = Base64.getEncoder().encodeToString(pdf);
			file.setFileString(base64EncodedImageBytes);
			file.setUrl(null);
//			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("application/pdf")).body(pdf);
			res.setData(file);
			res.setMsg("file get successfully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
}
