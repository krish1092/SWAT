package com.meteorology.swat.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;

/**
 * A utility class to handle DB configuration.
 * @author Krishnan Subramanian
 *
 */
public final class DBProperties {
	/**
	 * Prevent instantiation.
	 */
	private DBProperties() { }
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DBProperties.class);
	private static final Properties prop = new Properties();
	private static FileInputStream inputStream;
	private static String dbUrl;
	private static String dbUsername;
	private static String dbPassword;
	
	static {
		try {
			inputStream = new FileInputStream("./src/Connection.properties");
			prop.load(inputStream);
			dbUrl = prop.getProperty("url");
			dbUsername = prop.getProperty("username");
			dbPassword = prop.getProperty("password");
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * @return The database URL.
	 */
	public static String getDbUrl() {
		return dbUrl;
	}

	/**
	 * @return The database username.
	 */
	public static String getDbUsername() {
		return dbUsername;
	}

	/**
	 * @return The database password.
	 */
	public static String getDbPassword() {
		return dbPassword;
	}
}
