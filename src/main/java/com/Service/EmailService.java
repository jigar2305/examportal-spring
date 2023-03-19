package com.Service;

import com.Bean.EmailDetailsBean;

public interface EmailService {

	String sendSimpleMail(EmailDetailsBean details) throws Exception;

	String sendMailWithAttachment(EmailDetailsBean details) throws Exception;
}
