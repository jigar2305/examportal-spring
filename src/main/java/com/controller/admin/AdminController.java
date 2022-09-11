package com.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.UserBean;
import com.repository.UserRepository;

@CrossOrigin
@RequestMapping("/admin")
@RestController
public class AdminController {
	@Autowired
	UserRepository userRepo;

	@GetMapping("/userlist")
	public ResponseEntity<?> liststudents() {
		List<UserBean> users = (List<UserBean>) userRepo.findAll();
		ResponseBean<List<UserBean>> res = new ResponseBean<>();
		res.setData(users);
		res.setMsg("users fetch successfully..");
		return ResponseEntity.ok(res);
	}
	@GetMapping("/deleteuser/{userId}")
	public ResponseEntity<?> deleteuser(@PathVariable("userId")Integer userId){		
		userRepo.deleteById(userId);
		ResponseBean<Object> res = new ResponseBean<>();
		res.setData(userId);
		res.setMsg("user deleted successfully..");
		return ResponseEntity.ok(res);
	}
}
