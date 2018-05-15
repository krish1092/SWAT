package com.meteorology.swat.junk;
/*package com.meteorology.swat.util;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="com.meteorology.swat")
@EnableWebMvc
public class MVCConfiguration {

	
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/contactdb");
		dataSource.setUsername("krish");
		dataSource.setPassword("Swetha@1992");
		return dataSource;
	}
	
	
	@Bean
    public EventStorageDAO getContactDAO() {
        return new EventStorageDAOImpl(getDataSource());
    }
}
	
*/