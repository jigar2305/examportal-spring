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
	public Object signup(UserBean user) throws Exception {
		String email = user.getEmail();
		UserBean dbUser = userRepo.findByEmail(email);
		if (dbUser == null) {
			user.setActive(true);
			RoleBean role = roleRepo.findByRoleName("student");
			user.setRole(role);
			String encpassword = bcrypt.encode(user.getPassword());
			user.setPassword(encpassword);
			userRepo.save(user);
			return new ResponseBean(user, "Signup done", 200);
		} else {
			return new ResponseBean(user, "Email Already Taken", 404);
		}
	}

	@Override
	public Object login(LoginBean login) throws Exception {
		String email = login.getEmail();
		UserBean userBean = userRepo.findByEmail(email);
		if (userBean == null) {
			return new ResponseBean(login, "Not valid Email", 404);
		} else if (!bcrypt.matches(login.getPassword(), userBean.getPassword())) {
			return new ResponseBean(login, "Invalid Credentials", 401);
		} else {
			userBean.setAuthToken(tokenService.createtoken(20));
			userRepo.save(userBean);
			return new ResponseBean(userBean, "Login done", 200);
		}
	}

	@Override
	public Object sendotp(LoginBean login) throws Exception {
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
		return new ResponseBean(emailBean, "OTP send successfully", 200);
	}

	@Override
	public Object forgot(forgotpasswordBean forgotpassword) throws Exception {
		String email = forgotpassword.getEmail();
		UserBean userBean = userRepo.findByEmail(email);
		Integer otp = userBean.getOtp();
		if (otp == null) {
			return new ResponseBean(email, "Please Try Again", 404);
		} else if (otp.equals(forgotpassword.getOtp())) {
			userBean.setOtp(null);
			userRepo.save(userBean);
			return new ResponseBean(email, "OTP Varifyed", 200);
		} else {
			return new ResponseBean(email, "incorrect otp", 200);
		}
	}

	@Override
	public Object updatepassword(LoginBean login) throws Exception {
		UserBean userBean = userRepo.findByEmail(login.getEmail());
		userBean.setPassword(bcrypt.encode(login.getPassword()));
		userRepo.save(userBean);
		return new ResponseBean(userBean, "Password Changed Successfully", 200);
	}

	@Override
	public void logout(Integer userId) throws Exception {
		UserBean user = userRepo.findByUserId(userId);
		if (user != null) {
			user.setAuthToken(null);
			userRepo.save(user);
		}
	}

}
