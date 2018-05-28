package com.meteorology.swat.junk;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.meteorology.swat.DAO.UserDAO;
import com.meteorology.swat.DAOImpl.UserDAOImpl;



public class EncryptionAndHash {

	public static void main(String[] a) throws SQLException
	{
		/*long s1 = System.currentTimeMillis();
		String s = "Swat@2016";
		EncryptionAndHash eh = new EncryptionAndHash("krish1092");
		System.out.println("username exists? :"+eh.usernameExists());
		System.out.println(eh.passwordValidate(s.toCharArray()));
		long s2 = System.currentTimeMillis();
		System.out.println(s2 - s1);
		*/
		
		
		
		UserDAO userDAO = new UserDAOImpl();
		userDAO.setDataSource();
		System.out.println(userDAO.userAlreadyExists("k@kl.com"));
		
		
		
		
		
		/*
		Properties properties = System.getProperties();
		String host = "localhost";
		
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port","587");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.auth", "true");
		
		SimpleMailMessage smm  = new SimpleMailMessage();
		smm.setTo("swetharenyanatarajan@gmail.com");
		smm.setText("This mail is coming from the localhsot");
		Session session = Session.getInstance(properties,new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("krish@iastate.edu", "!23Ak7d1wef7");
			}
		});
		try{
			Message m = new MimeMessage(session);
			m.setFrom(new InternetAddress("krish@iastate.edu"));
			m.setRecipients(Message.RecipientType.TO, InternetAddress.parse("krish@iastate.edu"));
			m.setSubject("Automated Email Testing");
			m.setText("This is an automated email from the SWAT Team! It is working :)");
			Transport.send(m);
		}catch(MessagingException m){
			m.printStackTrace();
		}
		
		*/
		
	}
	
	private String userName;
	
	
	
	public boolean passwordValidate(char[] password,String hashedPasswordFromDB){
		
		boolean passwordCorrect;
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
		passwordCorrect = bCryptPasswordEncoder.matches(new String(password), hashedPasswordFromDB);
		return passwordCorrect;
		
	}
	
	public boolean usernameExists(){
		UserDAO userDAO = new UserDAOImpl();
		try{
			userDAO.setDataSource();
		}catch(Exception e){
			e.printStackTrace();
		}
		boolean usernameExistsAlready  = userDAO.getUsernameAndEmailID(userName);
		
		return usernameExistsAlready;
	}
	
	public DataSource dataSourceDetails() throws SQLException
	{
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
        dataSource.setDriver(new com.mysql.jdbc.Driver());
        dataSource.setUrl("jdbc:mysql://localhost:3306/swat");
        dataSource.setUsername("root");
        dataSource.setPassword("SwatTool@2015");
        //dataSource.setPassword("Swat@2016");
        return dataSource;
	}
	
	/*public String retrievePasswordFromDataBase(String userName)
	{
		UserDAO userDAO = new UserDAOImpl();
		try{
			userDAO.setDataSource();
		}catch(Exception e){
			e.printStackTrace();
			
		}
		String retrievedPassword = userDAO.getPassword(userName);
		return retrievedPassword;
	}*/
}




//Other method of Encryption

/*
try
{
	MessageDigest md = MessageDigest.getInstance("SHA-512");
	byte[] p  = md.digest(s.getBytes("UTF-8"));
	System.out.println(p.length);
	System.out.println(Arrays.toString(p));
}catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
	e.printStackTrace();
}*/