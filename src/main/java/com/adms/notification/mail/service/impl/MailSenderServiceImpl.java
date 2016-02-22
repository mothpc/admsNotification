package com.adms.notification.mail.service.impl;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.adms.notification.mail.service.MailSenderService;

@Service("mailSenderService")
public class MailSenderServiceImpl implements MailSenderService {

	@Autowired
	private MailSender mailSender;

	@Autowired
	private SimpleMailMessage templateMailMessage;

	@Autowired
	private JavaMailSenderImpl javaMailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;

	public void setMailSender(MailSender mailSender)
	{
		this.mailSender = mailSender;
	}

	public SimpleMailMessage getTemplateMailMessage()
	{
		return templateMailMessage;
	}

	public void setTemplateMailMessage(SimpleMailMessage templateMailMessage)
	{
		this.templateMailMessage = templateMailMessage;
	}

	public void setJavaMailSender(JavaMailSenderImpl javaMailSender)
	{
		this.javaMailSender = javaMailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine)
	{
		this.velocityEngine = velocityEngine;
	}

	public void sendMail(String from, String to, String subject, String msg)
	{
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setFrom(from);
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(msg);
		mailSender.send(simpleMailMessage);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sendMailTemplate(String velocityTemplateFile, Map model, FileSystemResource[] attachments)
	{
		MimeMessage message = javaMailSender.createMimeMessage();
		try
		{
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(templateMailMessage.getFrom());
			helper.setTo(templateMailMessage.getTo());
			helper.setSubject(templateMailMessage.getSubject());

			String mailBody = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityTemplateFile, "UTF-8", model);
			helper.setText(mailBody, true);

			if (attachments != null && attachments.length > 0)
			{
				for (FileSystemResource attachment : attachments)
				{
					helper.addAttachment(attachment.getFilename(), attachment);
				}
			}
		}
		catch (MessagingException e)
		{
			throw new MailParseException(e);
		}
		javaMailSender.send(message);
	}

}
