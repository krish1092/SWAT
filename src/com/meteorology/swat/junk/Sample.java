package com.meteorology.swat.junk;

import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.SpringVersion;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.meteorology.swat.DAO.UserDAO;
import com.meteorology.swat.DAOImpl.UserDAOImpl;
import com.meteorology.swat.bean.LoginForm;
import com.meteorology.swat.service.UserService;

public class Sample {

	public static void main(String[] args) throws SQLException
	{
		/*UserDAO u = new UserDAOImpl();
		//System.out.println(u.getPassword("k@kl.com"));
		UUID uu = UUID.randomUUID();
		System.out.println(uu);
		System.out.println(uu.toString());
//		System.out.println(SpringVersion.getVersion());
*/	
		
		/*
		UserService userService = new UserService();
		LoginForm l = new LoginForm();
		l.setEmailID("krish@krish.com");
		l.setPassword("Swat@2016".toCharArray());
		
		System.out.println(userService.login(l));*/
		
		
		
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port","25");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.auth", "true");
		
		
		
		/*properties.put("mail.smtp.host", "mailhub.iastate.edu");
		properties.put("mail.smtp.port","25");
		//properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.auth", "false");
		*/
		//Session session = Session.getDefaultInstance(properties);
		Session session = Session.getInstance(properties,new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("krish@iastate.edu", "!23Ak7d1wef7");
			}
		});
		try{
			MimeMessage m = new MimeMessage(session);
			//m.setFrom(new InternetAddress("noreply@cyclone.agron.iastate.edu"));
			m.setFrom(new InternetAddress("krish@iastate.edu"));
			m.setRecipients(Message.RecipientType.TO, InternetAddress.parse("krish@iastate.edu"));
			m.setSubject("Severe Weather Analysis Tool - Registration");
			StringBuffer s = new StringBuffer();
			s.append("Dear Krishnan,");
			s.append("<br><br>");
			s.append("<p style = \" padding-left:5em;\"	>		Welcome to Severe Weather Analysis Tool! "
					+ "Please click the following link to activate your account");
			s.append("<br><br>");
			s.append("</p>");			
			s.append("<Link comes here>");
			String charset = "UTF-8";
			m.setText(s.toString(),charset , "html");
			Transport.send(m);
			System.out.println("Sent");
		}catch(MessagingException m){
			m.printStackTrace();
		}
		
	
	}
	
}
