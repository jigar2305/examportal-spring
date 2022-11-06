package com.controller.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.EmailDetailsBean;
import com.bean.LoginBean;
import com.bean.ResponseBean;
import com.bean.RoleBean;
import com.bean.UserBean;
import com.bean.forgotpasswordBean;
import com.controller.EmailController;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.OptService;
import com.service.TokenService;

@CrossOrigin
@RequestMapping("/public")
@RestController
public class SessionController {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
	
	@Autowired
	EmailController emailController;
	
	@Autowired
	OptService optService;
	
	@Autowired
	TokenService tokenService;
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserBean user){
		String email = user.getEmail();
		UserBean dbUser= userRepo.findByEmail(email);
		ResponseBean<UserBean> res = new ResponseBean<>();
		if (dbUser == null) {
			user.setActive(true);
			RoleBean role = roleRepo.findByRoleName("student");
			user.setRole(role);
			String encpassword = bcrypt.encode(user.getPassword());
			user.setPassword(encpassword);	
			userRepo.save(user);
			res.setData(user);	
			res.setMsg("Signup done...");
			return ResponseEntity.ok(res);
		} else {
			res.setData(user);
			res.setMsg("Email Already Taken");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginBean login){
		String email = login.getEmail();
		UserBean userBean = userRepo.findByEmail(email);
		if(userBean == null || !bcrypt.matches(login.getPassword(),userBean.getPassword())) {

			ResponseBean<LoginBean> res = new ResponseBean<>();
			res.setData(login);
			res.setMsg("Invalid Credentials");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		}else {
			ResponseBean<UserBean> res = new ResponseBean<>();
			userBean.setAuthToken(tokenService.createtoken(20));
			userRepo.save(userBean);
			res.setData(userBean);
			res.setMsg("Login done...");
			return ResponseEntity.ok(res);
		}				
	}
	 
	@PostMapping("/otpsend")
	public ResponseEntity<?> sendotp(@RequestBody LoginBean login){
		EmailDetailsBean emailBean = new EmailDetailsBean();
		String email =  login.getEmail();
		UserBean userBean = userRepo.findByEmail(email);
		Integer otp = optService.genrateOtp();
		emailBean.setRecipient(email);
		emailBean.setSubject("forget password otp");
		emailBean.setMsgBody("forgot password OTP is-"+otp);
		emailController.sendMail(emailBean);
		userBean.setOtp(otp);
		userRepo.save(userBean);
		return ResponseEntity.ok(emailBean);
	}
	

	@PostMapping("/otp")
	public ResponseEntity<?> forgot(@RequestBody forgotpasswordBean forgotpassword){
		ResponseBean<Object> res = new ResponseBean<>();
		String email = forgotpassword.getEmail();
		UserBean userBean = userRepo.findByEmail(email);
		Integer otp = userBean.getOtp();
		if(otp == null ) {
			res.setData(email);
			res.setMsg("otp not found");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		}else if(otp.equals(forgotpassword.getOtp())) {
			res.setData(email);
			res.setMsg("successfully...");
			userBean.setOtp(null);
			userRepo.save(userBean);
			return ResponseEntity.ok(res);
		}else {
			res.setData(email);
			res.setMsg("incorrect otp");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		}
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<?> updatepassword(@RequestBody LoginBean login){
		ResponseBean<Object> res = new ResponseBean<>();
		UserBean userBean = userRepo.findByEmail(login.getEmail());
		userBean.setPassword(bcrypt.encode(login.getPassword()));
		userRepo.save(userBean);
		res.setData(userBean);
		res.setMsg("password successfully forgot...");
		return ResponseEntity.ok(res);	
	}
	
	@GetMapping("/logout/{userId}")
	public void logout(@PathVariable("userId")Integer userId){		
		UserBean user = userRepo.findByUserId(userId);
		System.out.println("called");
		user.setAuthToken(null);
		userRepo.save(user);
	}
	
	
}
