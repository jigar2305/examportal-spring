package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.UserBean;
import com.repository.UserRepository;

@Service
public class TokenService {

	@Autowired
	UserRepository userRepository;

	public String createtoken(int size) {

		String alph = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz!@#$%^&*";
		String token = "";
		UserBean dbuser = null;
		do {
			for (int i = 1; i <= size; i++) {
				int index = (int) (alph.length() * Math.random());
				token = token + alph.charAt(index);
			}
			dbuser = userRepository.findByAuthToken(token);
		} while (dbuser != null);
		return token;
	}

}
