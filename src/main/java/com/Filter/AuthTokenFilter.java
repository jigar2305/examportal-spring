package com.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Entity.UserBean;
import com.Repositoy.UserRepository;

@Component
public class AuthTokenFilter implements Filter {
	@Autowired
	UserRepository userRepository;
	

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = ((HttpServletResponse) res);
		String url = request.getRequestURL().toString();
		if (url.contains("/public/")|| request.getMethod().toLowerCase().equals("options")) {
			chain.doFilter(req, res);
		} else {
			String authToken = request.getHeader("authToken");
			String email = request.getHeader("email");
			UserBean user = userRepository.findByAuthTokenAndEmail(authToken,email);
			if (authToken == null || email == null || authToken.trim().length() != 20 || user == null) {
				response.setContentType("application/json");
				response.setStatus(401);
				response.getWriter().write("{'msg':'Please Login before access service'}");			
			} else {
				chain.doFilter(req, res);
			}
		}
	}
}
