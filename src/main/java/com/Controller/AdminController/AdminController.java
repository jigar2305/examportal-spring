package com.Controller.AdminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Service.AdminService;

@RequestMapping("/admin")
@RestController
public class AdminController {
	
	@Autowired
	AdminService adminService;

	@GetMapping("/userlist")
	public Object liststudents() throws Exception {
		return ResponseEntity.ok(adminService.listUser());
	}
	
	@DeleteMapping("/deleteuser/{userId}")
	public Object deleteuser(@PathVariable("userId")Integer userId) throws Exception {	
		return ResponseEntity.ok(adminService.deleteUser(userId));
	}
	
	@GetMapping("/check/{userId}")
	public Object check(@PathVariable("userId")Integer userId) throws Exception {		
		return ResponseEntity.ok(adminService.checkForDelete(userId));	
	}
	
	@GetMapping("/updatestatus/{userId}")
	public Object active(@PathVariable("userId")Integer userId) throws Exception {		
		return ResponseEntity.ok(adminService.isActive(userId));
	}
	@GetMapping("/user/{userId}")
	public Object findbyid(@PathVariable("userId")Integer userId) throws Exception {		
		return ResponseEntity.ok(adminService.findUserById(userId));
	}
	
	
}
