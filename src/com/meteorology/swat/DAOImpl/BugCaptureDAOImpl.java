package com.meteorology.swat.DAOImpl;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.meteorology.swat.DAO.BugCapture;
import com.meteorology.swat.bean.Bug;

public class BugCaptureDAOImpl implements BugCapture {

	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public void insertBug(Bug bug) {
		
		String sql = "insert into bugs values(?,?)";
		try{
			this.jdbcTemplate.update(sql,null,bug.getDescription());
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}

	@Override
	public void setDataSource() throws SQLException {

		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new com.mysql.jdbc.Driver());
        dataSource.setUrl("jdbc:mysql://localhost:3306/swat");
        dataSource.setUsername("root");
        dataSource.setPassword("SwatTool@2015");
        //dataSource.setPassword("Swat@2016");
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	

	}

}
