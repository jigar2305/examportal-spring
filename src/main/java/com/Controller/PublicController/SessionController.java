package com.Controller.PublicController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bean.LoginBean;
import com.Bean.forgotpasswordBean;
import com.Entity.UserBean;
import com.Service.SessionService;

@RequestMapping("/public")
@RestController
public class SessionController {

	@Autowired
	SessionService sessionService;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserBean user) {
		return ResponseEntity.ok(sessionService.signup(user));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginBean login) {
		return ResponseEntity.ok(sessionService.login(login));
	}

	@PostMapping("/otpsend")
	public ResponseEntity<?> sendotp(@RequestBody LoginBean login) {
		return ResponseEntity.ok(sessionService.sendotp(login));
	}

	@PostMapping("/otp")
	public ResponseEntity<?> forgot(@RequestBody forgotpasswordBean forgotpassword) {
		return ResponseEntity.ok(sessionService.forgot(forgotpassword));
	}

	@PostMapping("/forgot")
	public ResponseEntity<?> updatepassword(@RequestBody LoginBean login) {
		return ResponseEntity.ok(sessionService.updatepassword(login));
	}

	@GetMapping("/logout/{userId}")
	public void logout(@PathVariable("userId") Integer userId) {
		sessionService.logout(userId);
	}

}
