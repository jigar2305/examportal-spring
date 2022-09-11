package com.service;

import org.springframework.stereotype.Service;

@Service
public class OptService {	
	public Integer  genrateOtp() {	
		return (int) ((10000)*Math.random());
	}
}
