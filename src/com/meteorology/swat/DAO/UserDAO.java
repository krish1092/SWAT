package com.meteorology.swat.DAO;

import com.meteorology.swat.bean.SignUpForm;
import com.meteorology.swat.bean.UserDetails;

public interface UserDAO {
	
	public String getPassword(String emailAddress);
	
	public boolean getUsernameAndEmailID(String emailAddress);
	
	public void insertNewUser(SignUpForm s);
	
	public UserDetails getUserDetails(String emailAddress);
	
	public void updatePassword(String password, String emailAddress);
	
	//EMail ID cannot be encrypted and hashed using BCryptPasswordEncoder since there is no decoder for this.
	public void updateEmailID(char[] emailAddress);
	
	public void setDataSource();
	
	public boolean userAlreadyExists(String emailAddress);
	
	public boolean authenticate(String token);
	
	public String getEmailAddressWithChangePasswordToken(String token);
	
	public void invalidateChangePasswordToken(String token);
	
	public void insertUserAuth(String token, String emailAddress, String purpose);

}
