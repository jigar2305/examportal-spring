package com.Service;

import org.springframework.stereotype.Service;

@Service
public interface TokenService {

	String createtoken(int size) throws Exception;

	Integer genrateOtp() throws Exception;

}
