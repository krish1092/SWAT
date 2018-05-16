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

/**
 * Class that handles user related operations.
 * @author Krishnan Subramanian
 *
 */
public class UserService {
	
	private UserDetails userDetailsFromDB;
	private UUID uuid;
	private String url;
	private boolean passwordEmailSent,activationEmailSent;
	private Properties properties;
	
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
	
	public boolean isPasswordEmailSent() {
		return passwordEmailSent;
	}

	public boolean isActivationEmailSent() {
		return activationEmailSent;
	}

	/**
	 * Set the activation email sent to true or false.
	 * @param activationEmailSent
	 */
	private void setActivationEmailSent(boolean activationEmailSent) {
		this.activationEmailSent = activationEmailSent;
	}
	
	/**
	 * Set the properties related to sending the email.
	 */
	protected void setProperties()
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
	 * @param emailID
	 * @param name
	 */
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
			
		}catch(MessagingException m){
			m.printStackTrace();
		}
		
	}
	
	/**
	 * Signup for user.
	 * @param s The {@link SignUpForm}
	 * @throws SQLException When there's an issue connecting to the database.
	 */
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
	
	/**
	 * Encrypt the password of the user.
	 * @param password the given password.
	 * @return An encrypted version of the password.
	 */
	private String encodePassword(String password){
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
		return bCryptPasswordEncoder.encode(password);
	}
	
	/**
	 * Login to the application.
	 * @param login The {@link LoginForm} containing the email address and the password.
	 * @return True or false indicating if the login was successful.
	 * @throws SQLException When there's an issue connecting to the database.
	 */
	public boolean login(LoginForm login) throws SQLException{
		String encryptedPassword = encodePassword(login.getPassword().toString());
		login.setPassword(encryptedPassword.toCharArray());
		UserDAO userDAO = new UserDAOImpl();
		userDAO.setDataSource();
		return userDAO.validateUserLogin(login);
		/*userDetailsFromDB = userDAO.getUserDetails(login.getEmailID());
		if(userDetailsFromDB != null){
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
			return bCryptPasswordEncoder.matches(new String(login.getPassword()), userDetailsFromDB.getPassword());
		}
		else{
			return false;
		}*/
	}
	
	/**
	 * Generate a random UUID for a token.
	 */
	private void generateUUID(){
		uuid = UUID.randomUUID();
	}
	
	/**
	 * Authenticate the user when the click on the URI sent to them in the signup email.
	 * @param token The token value from the user.
	 * @return true or false indicating if the user was authenticated.
	 * @throws SQLException When there's an issue connecting to the database.
	 */
	public boolean authenticate(String token) throws SQLException{
		UserDAO userDAO = new UserDAOImpl();
		userDAO.setDataSource();
		return userDAO.authenticate(token);
	}

	/**
	 * Get the user's name.
	 * @return
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
	 * Send a change password email when the user clicks on forgot password.
	 * @param emailAddress The email address to send the user to.
	 */
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

	/**
	 * Construct the change password email.
	 * @param emailAddress The email address to send the email to.
	 */
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
