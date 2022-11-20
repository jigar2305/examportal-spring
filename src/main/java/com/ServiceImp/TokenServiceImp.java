package com.ServiceImp;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entity.UserBean;
import com.Repositoy.UserRepository;
import com.Service.TokenService;

@Service
public class TokenServiceImp implements TokenService {

	private static final Random Rand = new Random();

	@Autowired
	UserRepository userRepository;

	@Override
	public String createtoken(int size) {
		String alph = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz!@#$%^&*";
		String token = "";
		UserBean dbuser = null;
		do {
			for (int i = 1; i <= size; i++) {
				int index = Rand.nextInt(alph.length());
				token = token + alph.charAt(index);
			}
			dbuser = userRepository.findByAuthToken(token);
		} while (dbuser != null);
		return token;
	}

	@Override
	public Integer genrateOtp() {
		return (int) ((10000) * Math.random());
	}

}
