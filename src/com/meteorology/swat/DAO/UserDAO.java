package com.meteorology.swat.DAO;

import com.meteorology.swat.DAOImpl.UserDAOImpl;
import com.meteorology.swat.bean.SignUpForm;
import com.meteorology.swat.bean.UserDetails;

/**
 * The DAO to interact with user related schema.
 * @author Krishnan Subramanian
 *
 */
public interface UserDAO {
	
	/**
	 * To be removed
	 * @param emailAddress
	 * @return
	 */
	public String getPassword(String emailAddress);
	
	/**
	 * To be removed.
	 * @param emailAddress
	 * @return
	 */
	public boolean getUsernameAndEmailID(String emailAddress);
	
	/**
	 * Insert a new user given the signup form.
	 * @param s the {@link SignUpForm}
	 */
	public void insertNewUser(SignUpForm s);
	
	/**
	 * To be removed.
	 * @param emailAddress
	 * @return
	 */
	public UserDetails getUserDetails(String emailAddress);
	
	/**
	 * Update user password of an already logged in user.
	 * @param password The password to store.
	 * @param emailAddress The email address of the user.
	 */
	public void updatePassword(String password, String emailAddress);
	
	/**
	 * Uodate the email address.
	 * @param emailAddress The new email address to add.
	 */
	//EMail ID cannot be encrypted and hashed using BCryptPasswordEncoder since there is no decoder for this.
	public void updateEmailID(char[] emailAddress);
	
	/**
	 * To be privatized.
	 */
	public void setDataSource();
	
	/**
	 * Check if the user already exists.
	 * @param emailAddress The email address to lookup.
	 * @return true or false indicating if the user exists.
	 */
	public boolean userAlreadyExists(String emailAddress);
	
	/**
	 * Authenticate the given token.
	 * @param token The token to authenticate.
	 * @return True or false indicating if the authentication was successful.
	 */
	public boolean authenticate(String token);
	
	/**
	 * Get the email address given the token.
	 * @param token The token to get the email address for.
	 * @return The email address.
	 */
	public String getEmailAddressWithChangePasswordToken(String token);
	
	/**
	 * To be removed.
	 * @param token The token to be deleted.
	 */
	public void invalidateChangePasswordToken(String token);
	
	/**
	 * Insert the authentication token into the schema.
	 * @param token The token to be inserted.
	 * @param emailAddress The corresponding email address.
	 * @param purpose The purpose of the authentication token.
	 */
	public void insertUserAuth(String token, String emailAddress, String purpose);
	
	/**
	 * A factory class to get the Implementation.
	 *
	 */
	public static class Factory {
		/**
		 * Create a {@link UserDAO} object.
		 * @return a {@link UserDAO} object.
		 */
		public static UserDAO getDefaultInstance() {
			return new UserDAOImpl();
		}
	}
}
