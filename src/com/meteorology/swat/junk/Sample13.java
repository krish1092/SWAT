package com.meteorology.swat.junk;

import org.slf4j.Logger;

import com.meteorology.swat.util.DBProperties;

public class Sample13 {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Sample13.class);
	
	public static void main(String[] args) {
		
		logger.info(DBProperties.getDbUrl());
		logger.info(DBProperties.getDbUsername());
		logger.info(DBProperties.getDbPassword());
		
		
		/*try {

			FileInputStream properties = new FileInputStream("./src/Connection.properties");
			Properties prop = new Properties();
			prop.load(properties);
			System.out.println(prop.getProperty("url"));
			System.out.println(prop.getProperty("username"));
			System.out.println(prop.getProperty("password"));
			
			properties.close();

		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}
