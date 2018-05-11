package com.meteorology.swat.bean;

/*import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
*/
public class SignUpForm {

	
	/*@NotNull(message = "Please enter a valid Email ID")
	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&�*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")*/
	private String emailAddress;
	
	/*@NotNull(message = "Please enter a valid Name")*/
	private String name;
	
	/*@NotNull(message = "Please enter a valid Password")*/
	private char[] password;
	
	private String uuid;
	
	public String getEmailID() {
		return emailAddress;
	}

	public void setEmailID(String emailID) {
		this.emailAddress = emailID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	

}
