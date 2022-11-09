package com.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.UserBean;
import com.bean.forms.ExamBean;
import com.bean.forms.ResultBean;
import com.bean.forms.SubjectBean;
import com.repository.ExamRepository;
import com.repository.ResultRepository;
import com.repository.SubjectRepository;
import com.repository.UserRepository;

@CrossOrigin
@RequestMapping("/admin")
@RestController
public class AdminController {
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	SubjectRepository subjectRepo;
	
	@Autowired
	ExamRepository examRepo;
	
	@Autowired
	ResultRepository resultRepo;
	
	@GetMapping("/userlist")
	public ResponseEntity<?> liststudents() {
		List<UserBean> users = (List<UserBean>) userRepo.findAll();
		ResponseBean<List<UserBean>> res = new ResponseBean<>();
		res.setData(users);
		res.setMsg("users fetch successfully..");
		return ResponseEntity.ok(res);
	}
	
	@DeleteMapping("/deleteuser/{userId}")
	public ResponseEntity<?> deleteuser(@PathVariable("userId")Integer userId){	
		UserBean user = userRepo.findByUserId(userId);
		try{
			List<SubjectBean> subject = subjectRepo.findByUsers(user);
			List<ExamBean> exam = examRepo.findByUsers(user);
			List<ResultBean> result = resultRepo.findByUser(user);
			
			if(subject != null) {		
				for (int i = 0; i < subject.size(); i++) {
					subject.get(i).getUsers().remove(user);
				}
				subjectRepo.saveAll(subject);
			}else if(exam != null) {		
				for (int i = 0; i < exam.size(); i++) {
					exam.get(i).getUsers().remove(user);
				}
				examRepo.saveAll(exam);
			}else if(result != null) {		
				for (int i = 0; i < result.size(); i++) {
				resultRepo.delete(result.get(i));
				}
				
			}
			userRepo.deleteById(userId);
		}catch (Exception e) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(500);
			res.setMsg("Technical error occoured");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
		ResponseBean<Object> res = new ResponseBean<>();
		res.setData(userId);
		res.setMsg("user deleted successfully..");
		return ResponseEntity.ok(res);
	}
	@GetMapping("/check/{userId}")
	public ResponseEntity<?> check(@PathVariable("userId")Integer userId){		
		UserBean user = userRepo.findByUserId(userId);
		if(user == null) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(userId);
			res.setMsg("user not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}else {
			try{
				List<SubjectBean> subject = subjectRepo.findByUsers(user);
				List<ExamBean> exam = examRepo.findByUsers(user);
				
				if(subject == null && exam == null) {					
					ResponseBean<Integer> res = new ResponseBean<>();
					res.setData(userId);
					res.setMsg("No dependency found");
					return ResponseEntity.status(HttpStatus.OK).body(res);
				}else {
					ResponseBean<Map<String,List<?>>> res = new ResponseBean<>();
					Map<String,List<?>> ress = new HashMap<>();
					ress.put("exam", exam);
					ress.put("subject", subject);
					res.setData(ress);
					res.setMsg("Subject And exam found for user");
					return ResponseEntity.status(HttpStatus.OK).body(res);
				}
			}catch (Exception e) {
				ResponseBean<Integer> res = new ResponseBean<>();
				res.setData(500);
				res.setMsg("Technical error occoured");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
			}
		}
		
	}
	
	@GetMapping("/updatestatus/{userId}")
	public ResponseEntity<?> active(@PathVariable("userId")Integer userId){		
		UserBean user = userRepo.findByUserId(userId);
		if(user.getActive().booleanValue()==true) {
			user.setActive(false);
		}else {
			user.setActive(true);
		}
		userRepo.save(user);
		ResponseBean<Boolean> res = new ResponseBean<>();
		res.setData(user.getActive());
		res.setMsg("status updated successfully..");
		return ResponseEntity.ok(res);
	}
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> findbyid(@PathVariable("userId")Integer userId){		
		UserBean user = userRepo.findByUserId(userId);
		ResponseBean<UserBean> res = new ResponseBean<>();
		res.setData(user);
		res.setMsg("get user successfully..");
		return ResponseEntity.ok(res);
	}
	
	
}
