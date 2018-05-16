package com.meteorology.swat.DAOImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;

import com.meteorology.swat.DAO.UserDAO;
import com.meteorology.swat.bean.LoginForm;
import com.meteorology.swat.bean.SignUpForm;
import com.meteorology.swat.bean.UserDetails;
import com.meteorology.swat.rowmapper.UserMapper;

@Repository
public class UserDAOImpl implements UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void setDataSource() {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		try{
			dataSource.setDriver(new com.mysql.jdbc.Driver());
	        dataSource.setUrl("jdbc:mysql://localhost:3306/swat");
	        dataSource.setUsername("root");
	        dataSource.setPassword("SwatTool@2015");
	        //dataSource.setPassword("Swat@2016");
		}catch(Exception e){
			e.printStackTrace();
		}
        
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public String getPassword(String userEmail) {
		try
		{
			String sql="select password from users where email_address = ?";
			String passwordFromDB = jdbcTemplate.queryForObject(sql, String.class, userEmail);
			return passwordFromDB;
		}catch(NullPointerException | EmptyResultDataAccessException e)
		{
			
			return null;
		}
		
	}

	@Override
	public boolean getUsernameAndEmailID(String userName) {
		
		
			String sql = "select username,email_address from users where username = ?";
			try{
				jdbcTemplate.queryForObject(sql, String.class, userName);
				return true;
			}catch(NullPointerException | EmptyResultDataAccessException e)
			{
				return false;
			}
	}
	
	@Override
	public void insertNewUser(SignUpForm s) {
		String sql = "insert into users(email_address,password,name,authenticated) values (?,?,?,?)";
		try{
			//authenticated = 0 ==> The user is yet to activate their account;
			this.jdbcTemplate.update(sql,s.getEmailID(),new String(s.getPassword()),s.getName(),0);
		}catch(Exception e){
			e.printStackTrace();
		}
				
	}

	@Override
	public void updatePassword(String password, String emailAddress) {
		
		String sql = "update users set users.password = ? where users.email_address = ? ";
		try{
			jdbcTemplate.update(sql, new Object[]{password,emailAddress});
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateEmailID(char[] emailID) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean userAlreadyExists(String emailID) {
		
		String sql = "select exists(select 1 from users where users.email_address = ? limit 1)";
		int userExistsInDB = jdbcTemplate.queryForObject(sql,Integer.class, emailID);
		if(userExistsInDB == 1) return true;
		else return false;		
	}
	
	@Override
	public boolean validateUserLogin(LoginForm loginForm) {
		String sql = "select exists ( select 1 from users where email_address = ? AND password = ? limit 1)";
		String password = new String(loginForm.getPassword());
		int validLogin = jdbcTemplate.queryForObject(sql,Integer.class, new Object[]{loginForm.getEmailID(), password});
		if(validLogin == 1) return true;
		else return false;
	}

	@Override
	public UserDetails getUserDetails(String userName) {
		
		String sql = "select name, password from users where email_address = ?";
		
		
		try{
			UserDetails userDetails = jdbcTemplate.queryForObject(sql, new Object[]{userName},new UserMapper());
			return userDetails;
		}catch(NullPointerException | EmptyResultDataAccessException e)
		{
			return null;
		}
		
		
		
	}

	@Override
	public boolean authenticate(String token) {
		String sql = "update users set users.authenticated = 1 "
				+ "where users.email_address = "
				+ "(select email_address from user_auth where user_auth.token = ? AND user_auth.auth_purpose = 'activation');";
		try{
			jdbcTemplate.update(sql, new Object[]{token});
			
			sql = "delete from user_auth where token = ? AND user_auth.auth_purpose = 'activation'";
			jdbcTemplate.update(sql, new Object[]{token});
			return true;
		}catch(DataAccessException e){
			return false;
		}
	}

	@Override
	public String getEmailAddressWithChangePasswordToken(String token) {
		String sql = "select email_address from user_auth where user_auth.token = ? AND user_auth.auth_purpose = 'forgot'";
		String emailAddress = jdbcTemplate.queryForObject(sql, new Object[]{token}, String.class);
		return emailAddress;
	}

	@Override
	public void invalidateChangePasswordToken(String token) {
		
		String sql = "delete from user_auth where token = ? ";
		try{
			jdbcTemplate.update(sql, new Object[]{token});
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void insertUserAuth(String token, String emailAddress, String purpose) {
		String sql = "insert into user_auth (email_address,token, auth_purpose) values (?,?,?)";
		try{
			jdbcTemplate.update(sql, emailAddress, token, purpose);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


}
