package com.adms.notification;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

import com.adms.notification.mail.service.MailSenderService;

public class App {

	public static void main(String[] args)
	{
		System.out.println("Hello World!");

		Map model = new HashMap();
		String velocityTemplateFile = "DailyReport_MTI_KBANK.vm";
		model.put("mailContent", "29/04/2015");
		FileSystemResource[] attachments = new FileSystemResource[2];
		attachments[0] = new FileSystemResource("D:\\temp\\attachment test.txt");
		attachments[1] = new FileSystemResource("D:\\temp\\attachment test 2.txt");

		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-notification.xml");
		MailSenderService mss = (MailSenderService) context.getBean("mailSenderService");
		/*mss.sendMail("kampon8@hotmail.com", "kampon8@gmail.com", "Java email test", "Testing.. \n Hello Spring Email Sender");*/
		mss.sendMailTemplate(velocityTemplateFile, model, attachments);
		context.close();
	}
}
