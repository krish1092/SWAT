package com.meteorology.swat.service;


import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.meteorology.swat.DAO.UserDAO;
import com.meteorology.swat.DAOImpl.UserDAOImpl;
import com.meteorology.swat.bean.LoginForm;
import com.meteorology.swat.bean.SignUpForm;
import com.meteorology.swat.bean.UserDetails;


public class UserService {
	
	public String getUrl() {
		return url;
	}

	private UserDetails userDetailsFromDB;
	
	public void setUrl(HttpServletRequest request,String view){
		String url = "http://"+
				/*request.getServerName()*/ "meteor.geol.iastate.edu"+
				/*":"*/""+
				/*Integer.toString(request.getServerPort())*/""+
				request.getContextPath()+
				"/"+view+"?token=";
		this.url = url;
	}

	private UUID uuid;
	private String url;
	private boolean passwordEmailSent,activationEmailSent;
	

	public boolean isPasswordEmailSent() {
		return passwordEmailSent;
	}

	public void setPasswordEmailSent(boolean passwordEmailSent) {
		this.passwordEmailSent = passwordEmailSent;
	}

	public boolean isActivationEmailSent() {
		return activationEmailSent;
	}

	public void setActivationEmailSent(boolean activationEmailSent) {
		this.activationEmailSent = activationEmailSent;
	}

	private Properties properties;
	
	public Properties getProperties() {
		return properties;
	}

	protected void setProperties()
	{
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "mailhub.iastate.edu");
		properties.put("mail.smtp.port","25");
		//properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.auth", "false");
		this.properties = properties;
	}
	
	
	private void sendUserActivationEmail(String emailID,String name){
		setProperties();
		Session session = Session.getDefaultInstance(properties);/*
		Session session = Session.getInstance(properties,new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("krish@iastate.edu", "password");
			}
		});*/
		
		try{
			MimeMessage  m = new MimeMessage(session);
			
			//From address
			m.setFrom(new InternetAddress("noreply@cyclone.agron.iastate.edu"));
			
			//To address
			m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailID));
			
			//Email Contents
			m.setSubject("Severe Weather Analysis Tool - Registration");
			StringBuffer s = new StringBuffer();
			s.append("Hello "+ name+",");
			s.append("<br><br>");
			s.append("<p style = \" padding-left:5em;\"	>"
					+ "Welcome to Severe Weather Analysis Tool! "
					+ "Please click the following link to activate your account");
			s.append("<br><br>");
			s.append("</p>");
			url = url + this.uuid.toString();
			System.out.println(url);
			System.out.println("<a target=\"_blank\" href=\""+url+ "\"" + ">" + url +"</a>");
			s.append("<a "
					+ "target=\"_blank\" "
					+ "href=\""+url+ "\"" 
					+ ">" 
					+ url 
					+"</a>"
					);
			String charset = "UTF-8";
			m.setText(s.toString(),charset , "html");
			Transport.send(m);
			
			//Transport.send(m);
		}catch(MessagingException m){
			m.printStackTrace();
		}
		
	}
	
	public void signup(SignUpForm s) throws SQLException{
		UserDAOImpl userDAO = new UserDAOImpl();
		userDAO.setDataSource();
		
		//Encoding the password using BCrypt algorithm!
		String encodedPassword = encodePassword(new String(s.getPassword()));
		s.setPassword(encodedPassword.toCharArray());
		
		//Auth Token for the user!
		generateUUID();
		s.setUuid(uuid.toString());
		
		//Insert into table
		userDAO.insertNewUser(s);
		userDAO.insertUserAuth(uuid.toString(), s.getEmailID(), "activation");
		
		//Send auth email to user
		sendUserActivationEmail(s.getEmailID(),s.getName());
	}
	
	
	public String encodePassword(String password){
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
		return bCryptPasswordEncoder.encode(password);
	}
	
	public boolean login(LoginForm login) throws SQLException{
		UserDAO userDAO = new UserDAOImpl();
		userDAO.setDataSource();
		userDetailsFromDB = userDAO.getUserDetails(login.getEmailID());
		if(userDetailsFromDB != null){
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
			return bCryptPasswordEncoder.matches(new String(login.getPassword()), userDetailsFromDB.getPassword());
		}
		else{
			return false;
		}
	}
	
	private void generateUUID(){
		uuid = UUID.randomUUID();
	}
	
	public boolean authenticate(String token) throws SQLException{
		UserDAO userDAO = new UserDAOImpl();
		userDAO.setDataSource();
		return userDAO.authenticate(token);
	}

	public String getName() {
		return userDetailsFromDB.getName();
	}
	
	public boolean changePassword(String token, String password){
		
		UserDAO userDAO = new UserDAOImpl();
		userDAO.setDataSource();
		String emailAddress = userDAO.getEmailAddressWithChangePasswordToken(token);
		if(emailAddress == null)
		{
			return false;
		}
		else
		{
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
			String encryptedPassword = bCryptPasswordEncoder.encode(password);
			userDAO.updatePassword(encryptedPassword, emailAddress);
			userDAO.invalidateChangePasswordToken(token);
			return true;
		}
	}
	
	public void forgotPassword(String emailAddress){
		UserDAO userDAO = new UserDAOImpl();
		userDAO.setDataSource();
		boolean userExists = userDAO.userAlreadyExists(emailAddress);
		 if(userExists)
		 {
			 generateUUID();
			 changePasswordEmail(emailAddress);
			 
			 userDAO.insertUserAuth(uuid.toString(), emailAddress , "forgot");
			 
			 
			 setActivationEmailSent(true);
		 }
		 else
		 {
			 setActivationEmailSent(false);
		 }
		 	 
	}

	private void changePasswordEmail(String emailAddress){
		setProperties();
		Session session = Session.getDefaultInstance(properties);
		try{
			MimeMessage  m = new MimeMessage(session);
			
			//From address
			m.setFrom(new InternetAddress("noreply@cyclone.agron.iastate.edu"));
			
			//To address
			m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
			
			//Email Contents
			m.setSubject("Severe Weather Analysis Tool - Password Change");
			
			//HTML Content for Email 
			StringBuffer s = new StringBuffer();
			s.append("Hello,");
			s.append("<br><br>");
			s.append("<p style = \" padding-left:5em;\"	>"
					+ "Please click the following link to change your password");
			s.append("<br><br>");
			s.append("</p>");
			url = url + this.uuid.toString();
			System.out.println(url);
			System.out.println("<a target=\"_blank\" href=\""+url+ "\"" + ">" + url +"</a>");
			s.append("<a "
					+ "target=\"_blank\" "
					+ "href=\""+url+ "\"" 
					+ ">" 
					+ url 
					+"</a>"
					);
			String charset = "UTF-8";
			m.setText(s.toString(),charset , "html");
			Transport.send(m);
		}catch(MessagingException m){
			m.printStackTrace();
		}
		
	}
}
