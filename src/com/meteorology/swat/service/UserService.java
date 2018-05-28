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

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.meteorology.swat.DAO.UserDAO;
import com.meteorology.swat.DAOImpl.UserDAOImpl;
import com.meteorology.swat.bean.LoginForm;
import com.meteorology.swat.bean.SignUpForm;
import com.meteorology.swat.bean.UserDetails;
import com.meteorology.swat.controller.LoginController;

/**
 * Class that handles user related operations.
 * @author Krishnan Subramanian
 *
 */
public class UserService {
	
	private UserDetails userDetailsFromDB;
	private String url;
	private Properties properties;
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * Signup for user.
	 * @param signupForm The {@link SignUpForm}
	 * @throws SQLException When there's an issue connecting to the database.
	 */
	public void signup(SignUpForm signupForm) throws DataAccessException, MessagingException {
		
		logger.info("User signing up:" + signupForm.getEmailID());
		
		UserDAO userDAO = UserDAO.Factory.getDefaultInstance();
		userDAO.setDataSource();
		
		//Encoding the password using BCrypt algorithm
		String encodedPassword = encodePassword(new String(signupForm.getPassword()));
		signupForm.setPassword(encodedPassword.toCharArray());
		
		//Auth Token for the user!
		String uuId = UUID.randomUUID().toString();
		signupForm.setUuid(uuId);
		
		//Insert into table
		userDAO.insertNewUser(signupForm);
		userDAO.insertUserAuth(uuId, signupForm.getEmailID(), "activation");
		
		//Send auth email to user
		sendUserActivationEmail(signupForm.getEmailID(),signupForm.getName(), uuId);
	}
	
	/**
	 * Login to the application.
	 * @param login The {@link LoginForm} containing the email address and the password.
	 * @return True or false indicating if the login was successful.
	 * @throws SQLException When there's an issue connecting to the database.
	 */
	public boolean login(LoginForm login) throws SQLException {
		logger.info("User logging in:" + login.getEmailID());
		
		UserDAO userDAO = UserDAO.Factory.getDefaultInstance();
		userDAO.setDataSource();
		userDetailsFromDB = userDAO.getUserDetails(login.getEmailID());
		if(userDetailsFromDB != null) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
			return bCryptPasswordEncoder.matches(new String(login.getPassword()), userDetailsFromDB.getPassword());
		} else {
			return false;
		}
	}
	
	/**
	 * The url to be constructed for the user to receive the email.
	 * @param request
	 * @param view
	 */
	public void setUrl(HttpServletRequest request,String view){
		String url = "http://"+
				/*request.getServerName()*/ "meteor.geol.iastate.edu"+
				/*":"*/""+
				/*Integer.toString(request.getServerPort())*/""+
				request.getContextPath()+
				"/"+view+"?token=";
		this.url = url;
	}
	
	/**
	 * Authenticate the user for signup/password change with the given token.
	 * @param token The token value from the user.
	 * @return true or false indicating if the user was authenticated.
	 * @throws SQLException When there's an issue connecting to the database.
	 */
	public boolean activateToken(String token) throws DataAccessException {
		UserDAO userDAO = UserDAO.Factory.getDefaultInstance();
		userDAO.setDataSource();
		return userDAO.activateToken(token);
	}
	
	/**
	 * Send a change password email when the user clicks on forgot password.
	 * @param emailAddress The email address to send the user to.
	 */
	public void forgotPassword(String emailAddress) {
		UserDAO userDAO = new UserDAOImpl();
		userDAO.setDataSource();
		boolean userExists = userDAO.userAlreadyExists(emailAddress);
		 if(userExists)
		 {
			 String token = UUID.randomUUID().toString();
			 changePasswordEmail(emailAddress, token);
			 userDAO.insertUserAuth(token, emailAddress , "forgot");
		 }	 
	}
	
	/**
	 * Set the properties related to sending the email.
	 */
	private void setProperties()
	{
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "mailhub.iastate.edu");
		properties.put("mail.smtp.port","25");
		//properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.auth", "false");
		this.properties = properties;
	}
	
	/**
	 * Send the user activation email.
	 * @param emailAddress
	 * @param name
	 */
	private void sendUserActivationEmail(String emailAddress, String name, String token) throws MessagingException {
		setProperties();
		Session session = Session.getDefaultInstance(properties);

		MimeMessage m = new MimeMessage(session);

		// From address
		m.setFrom(new InternetAddress("noreply@cyclone.agron.iastate.edu"));

		// To address
		m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));

		// Email Contents
		m.setSubject("Severe Weather Analysis Tool - Registration");
		StringBuffer s = new StringBuffer();
		s.append("Hello " + name + ",");
		s.append("<br><br>");
		s.append("<p style = \" padding-left:5em;\"	>" + "Welcome to Severe Weather Analysis Tool! "
				+ "Please click the following link to activate your account");
		s.append("<br><br>");
		s.append("</p>");
		url = url + token;
		s.append("<a " + "target=\"_blank\" " + "href=\"" + url + "\"" + ">" + url + "</a>");
		String charset = "UTF-8";
		m.setText(s.toString(), charset, "html");
		Transport.send(m);
	}
	
	/**
	 * Encrypt the password of the user.
	 * @param password the given password.
	 * @return An encrypted version of the password.
	 */
	private String encodePassword(String password) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
		return bCryptPasswordEncoder.encode(password);
	}

	/**
	 * Get the user's name.
	 * @return The user's name.
	 */
	public String getName() {
		return userDetailsFromDB.getName();
	}
	
	/**
	 * Change the password of the user.
	 * @param token The token the user clicked on.
	 * @param password The new password of the user.
	 * @return true or false indicating if the password was changed.
	 */
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
	
	

	/**
	 * Construct the change password email.
	 * @param emailAddress The email address to send the email to.
	 */
	private void changePasswordEmail(String emailAddress, String token){
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
			url = url + token;
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
	
	/*
	Session session = Session.getInstance(properties,new javax.mail.Authenticator(){
		protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("krish@iastate.edu", "password");
		}
	});*/
}
