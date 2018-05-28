package com.meteorology.swat.junk;

import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.meteorology.swat.DAO.UserDAO;

public class Sample7 {

	public static void main(String[] a) {
		
		UserDAO userDAO = UserDAO.Factory.getDefaultInstance();
		//userDAO.validateLogin("krish1610@gmail.com", "");
		//$2a$10$hZ3GLfFo7QSZRPUkib2.L.t4G7KaGl59cPgk/JT.OJ3yzkm34mdkO
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
		userDAO.setDataSource();
		String s = bCryptPasswordEncoder.encode("Swetha@1992");
		String value = userDAO.getPassword("krish1610@gmail.com");
		String encoded = "$2a$10$hZ3GLfFo7QSZRPUkib2.L.t4G7KaGl59cPgk/JT.OJ3yzkm34mdkO";
		boolean sss = bCryptPasswordEncoder.matches("Swetha@1992", encoded);
		//System.out.println(value);
		System.out.println(sss);
		
		// Existing
		//$2a$10$hZ3GLfFo7QSZRPUkib2.L.t4G7KaGl59cPgk/JT.OJ3yzkm34mdkO
		//$2a$10$hZ3GLfFo7QSZRPUkib2.L.t4G7KaGl59cPgk/JT.OJ3yzkm34mdkO
	}
}

