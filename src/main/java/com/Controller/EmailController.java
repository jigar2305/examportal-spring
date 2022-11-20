package com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.Bean.EmailDetailsBean;
import com.Service.EmailService;

@Controller
public class EmailController {

	@Autowired
	EmailService emailService;

	public String sendMail(@RequestBody EmailDetailsBean details) {
		return emailService.sendSimpleMail(details);
	}

	public String sendMailWithAttachment(@RequestBody EmailDetailsBean details) {
		return emailService.sendMailWithAttachment(details);
	}
}
