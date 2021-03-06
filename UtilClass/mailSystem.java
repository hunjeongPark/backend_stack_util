-- SimpleMail Send Controller Java Class Settings --

##########################################################################################
-- Controller --
##########################################################################################
package com.entropykorea.biztalkmng.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	private JavaMailSender sender;
	/*
	 * Email 발송 시스템
	 * */
//	@RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
//	public Map<String, Object> sendEmail(HttpServletRequest req) {
//		return emailUtil.sendEmail("test");
//	}
	@RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
	public Map<String, Object> sendEmail(HttpServletRequest req) {
		Map<String, Object> result = new HashMap<String, Object>();
		SimpleMailMessage msg = new SimpleMailMessage();
		
		ArrayList<String> sendToEmail = new ArrayList<String>();
		sendToEmail.add("hjpark@entropykorea.com");
		
		try {
			msg.setTo(sendToEmail.toArray(new String[sendToEmail.size()]));
			msg.setSubject("테스트");
			msg.setText("test");
			result.put("resultCode", 200);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("resultCode", 500);
		}
		
		sender.send(msg);
		return result;
	}
}

##########################################################################################
application.properties file settings
## Spring Gmail Send
##########################################################################################
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.protocol=smtp
spring.mail.default-encoding=UTF-8
spring.mail.username=qkrgjswjd100@gmail.com
spring.mail.password=[pw]
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.smtp.auth=true

implements gradle
 - implementation 'org.springframework.boot:spring-boot-starter-mail'
