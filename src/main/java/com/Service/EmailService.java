package com.Service;

import com.Bean.EmailDetailsBean;

public interface EmailService {
	
	String sendSimpleMail(EmailDetailsBean details);

	String sendMailWithAttachment(EmailDetailsBean details);
}
