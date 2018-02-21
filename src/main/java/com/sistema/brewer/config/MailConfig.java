package com.sistema.brewer.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.sistema.brewer.mail.Mailer;

@Configuration
@ComponentScan(basePackageClasses = Mailer.class)
@PropertySource({ "classpath:env/mail-${ambiente:local}.properties" })
@PropertySource(value = { "file://${HOME}/.brewer-mail.properties" }, ignoreResourceNotFound = true)
public class MailConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public JavaMailSender mailSend() {
		JavaMailSenderImpl mailSend = new JavaMailSenderImpl();
		mailSend.setHost("smtp.sendgrid.net");//endereço do servidor // da gmail "smtp.gmail.com"
		mailSend.setPort(587);//porta do servidor
		mailSend.setUsername(env.getProperty("email.username"));
		mailSend.setPassword(env.getProperty("email.password"));
		
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.connectiontimeout", 10000);//miliseconds
		
		mailSend.setJavaMailProperties(props);
		
		return mailSend;
	}

}
