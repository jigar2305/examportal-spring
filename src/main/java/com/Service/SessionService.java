package com.Service;

import org.springframework.stereotype.Service;

import com.Bean.LoginBean;
import com.Bean.ResponseBean;
import com.Bean.forgotpasswordBean;
import com.Entity.UserBean;

@Service
public interface SessionService {
	ResponseBean<?> signup(UserBean user);
	
	ResponseBean<?> login(LoginBean login);
		
	ResponseBean<?> sendotp(LoginBean login);

	ResponseBean<?> forgot(forgotpasswordBean forgotpassword);
	
	ResponseBean<?> updatepassword(LoginBean login);
	
	void logout(Integer userId);	
}
