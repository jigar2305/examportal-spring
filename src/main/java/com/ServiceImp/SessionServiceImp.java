package com.ServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Bean.EmailDetailsBean;
import com.Bean.LoginBean;
import com.Bean.ResponseBean;
import com.Bean.forgotpasswordBean;
import com.Controller.EmailController;
import com.Entity.RoleBean;
import com.Entity.UserBean;
import com.Repositoy.RoleRepository;
import com.Repositoy.UserRepository;
import com.Service.SessionService;
import com.Service.TokenService;

@Service
public class SessionServiceImp implements SessionService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	BCryptPasswordEncoder bcrypt;

	@Autowired
	EmailController emailController;

	@Autowired
	TokenService tokenService;

	@Override
	public ResponseBean<?> signup(UserBean user) {
		String email = user.getEmail();
		UserBean dbUser = userRepo.findByEmail(email);
		ResponseBean<UserBean> res = new ResponseBean<>();
		if (dbUser == null) {
			user.setActive(true);
			RoleBean role = roleRepo.findByRoleName("student");
			user.setRole(role);
			String encpassword = bcrypt.encode(user.getPassword());
			user.setPassword(encpassword);
			userRepo.save(user);
			res.setData(user);
			res.setMsg("Signup done.");
			res.setApicode(200);
			return res;
		} else {
			res.setData(user);
			res.setApicode(404);
			res.setMsg("Email Already Taken");
			return res;
		}
	}

	@Override
	public ResponseBean<?> login(LoginBean login) {
		String email = login.getEmail();
		UserBean userBean = userRepo.findByEmail(email);
		if (userBean == null) {
			ResponseBean<LoginBean> res = new ResponseBean<>();
			res.setData(login);
			res.setMsg("Not valid Email");
			res.setApicode(404);
			return res;
		} else if (!bcrypt.matches(login.getPassword(), userBean.getPassword())) {
			ResponseBean<LoginBean> res = new ResponseBean<>();
			res.setData(login);
			res.setMsg("Invalid Credentials");
			res.setApicode(401);
			return res;
		} else {
			ResponseBean<UserBean> res = new ResponseBean<>();
			userBean.setAuthToken(tokenService.createtoken(20));
			userRepo.save(userBean);
			res.setData(userBean);
			res.setMsg("Login done...");
			res.setApicode(200);
			return res;
		}
	}

	@Override
	public ResponseBean<?> sendotp(LoginBean login) {
		EmailDetailsBean emailBean = new EmailDetailsBean();
		String email = login.getEmail();
		UserBean userBean = userRepo.findByEmail(email);
		Integer otp = tokenService.genrateOtp();
		emailBean.setRecipient(email);
		emailBean.setSubject("forget password otp");
		emailBean.setMsgBody("forgot password OTP is-" + otp);
		emailController.sendMail(emailBean);
		userBean.setOtp(otp);
		userRepo.save(userBean);
		ResponseBean<EmailDetailsBean> res = new ResponseBean<>();
		res.setApicode(200);
		res.setData(emailBean);
		res.setMsg("OTP send successfully");
		return res;
	}

	@Override
	public ResponseBean<?> forgot(forgotpasswordBean forgotpassword) {
		ResponseBean<Object> res = new ResponseBean<>();
		String email = forgotpassword.getEmail();
		UserBean userBean = userRepo.findByEmail(email);
		Integer otp = userBean.getOtp();
		if (otp == null) {
			res.setData(email);
			res.setMsg("Please Try Again");
			res.setApicode(404);
			return res;
		} else if (otp.equals(forgotpassword.getOtp())) {
			userBean.setOtp(null);
			userRepo.save(userBean);
			res.setData(email);
			res.setMsg("OTP Varifyed");
			res.setApicode(200);
			return res;
		} else {
			res.setData(email);
			res.setMsg("incorrect otp");
			return res;
		}
	}

	@Override
	public ResponseBean<?> updatepassword(LoginBean login) {
		ResponseBean<UserBean> res = new ResponseBean<>();
		UserBean userBean = userRepo.findByEmail(login.getEmail());
		userBean.setPassword(bcrypt.encode(login.getPassword()));
		userRepo.save(userBean);
		res.setData(userBean);
		res.setMsg("Password Change Successfully");
		res.setApicode(200);
		return res;
	}

	@Override
	public void logout(Integer userId) {
		UserBean user = userRepo.findByUserId(userId);
		if (user != null) {
			user.setAuthToken(null);
			userRepo.save(user);
		}
	}

}
