package com.adms.notification.mail.service;

import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;

public interface MailSenderService {

	public SimpleMailMessage getTemplateMailMessage();

	public void sendMail(String from, String to, String subject, String msg);

	@SuppressWarnings("rawtypes")
	public void sendMailTemplate(String velocityTemplateFile, Map model, FileSystemResource[] attachments);
}
