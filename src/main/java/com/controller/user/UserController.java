package com.controller.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.RoleBean;
import com.bean.UserBean;
import com.repository.RoleRepository;
import com.repository.UserRepository;

@RequestMapping("/user")
@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@DeleteMapping("/deleteuser/:userId")
	public ResponseEntity<?> deleteuser(@PathVariable Integer userId){		
		userRepo.deleteById(userId);
		ResponseBean<Object> res = new ResponseBean<>();
		res.setData(userId);
		res.setMsg("user deleted successfully..");
		return ResponseEntity.ok(res);
	}
	@GetMapping("/getuser/{userId}")
	public ResponseEntity<?> getuserbyid(@PathVariable("userId") Integer userId){		
		Optional<UserBean> user = userRepo.findById(userId);
		ResponseBean<Object> res = new ResponseBean<>();
		res.setData(user);
		res.setMsg("get user successfully..");
		return ResponseEntity.ok(res);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateuser(@RequestBody UserBean user){
		RoleBean role = roleRepo.findByRoleName("student");
		user.setRole(role);
		userRepo.save(user);
		ResponseBean<Object> res = new ResponseBean<>();
		res.setData(user);
		res.setMsg("user updated successfully..");
		return ResponseEntity.ok(res);
	}
}
