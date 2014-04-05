package com.appspot.gardemallorie.spring.mail;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class GaeMailSender extends JavaMailSenderImpl implements MailSender {
	
	private Session session;
	
	@PostConstruct
	void init() {
		Properties props = new Properties();
		session = Session.getDefaultInstance(props, null);
	}

	@Override
	public Session getSession() {
		return session;
	}

	@Override
	protected void doSend(MimeMessage[] mimeMessages, Object[] originalMessages) throws MailException {
		for (MimeMessage mimeMessage : mimeMessages) {
		    try {
				Transport.send(mimeMessage);
			} catch (MessagingException e) {
				throw new MailSendException("Unable to send message", e);
			}
		}
	}

}
