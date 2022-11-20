package com.Controller.AdminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Service.AdminService;



@CrossOrigin
@RequestMapping("/admin")
@RestController
public class AdminController {
	
	@Autowired
	AdminService adminService;

	@GetMapping("/userlist")
	public ResponseEntity<?> liststudents() {
		return ResponseEntity.ok(adminService.listUser());
	}
	
	@DeleteMapping("/deleteuser/{userId}")
	public ResponseEntity<?> deleteuser(@PathVariable("userId")Integer userId){	
		return ResponseEntity.ok(adminService.deleteUser(userId));
	}
	
	@GetMapping("/check/{userId}")
	public ResponseEntity<?> check(@PathVariable("userId")Integer userId){		
		return ResponseEntity.ok(adminService.checkForDelete(userId));	
	}
	
	@GetMapping("/updatestatus/{userId}")
	public ResponseEntity<?> active(@PathVariable("userId")Integer userId){		
		return ResponseEntity.ok(adminService.isActive(userId));
	}
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> findbyid(@PathVariable("userId")Integer userId){		
		return ResponseEntity.ok(adminService.findUserById(userId));
	}
	
	
}
