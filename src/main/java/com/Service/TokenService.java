package com.Service;

import org.springframework.stereotype.Service;

@Service
public interface TokenService {
	
	String createtoken(int size);
	
	Integer  genrateOtp();

}
