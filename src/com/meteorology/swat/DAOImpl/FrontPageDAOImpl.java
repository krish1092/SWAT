package com.meteorology.swat.DAOImpl;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.meteorology.swat.DAO.FrontPageDAO;

public class FrontPageDAOImpl implements FrontPageDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void setDataSource() {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        try{
        	dataSource.setDriver(new com.mysql.jdbc.Driver());
        	dataSource.setUrl("jdbc:mysql://localhost:3306/swat");
        	dataSource.setUsername("root");
        	dataSource.setPassword("SwatTool@2015");
        	this.jdbcTemplate = new JdbcTemplate(dataSource);
        }catch(SQLException e){
        	this.jdbcTemplate = null;
        }
		
	}

	@Override
	public long getUserCount() {
		String sql = "select count(*) as users_count from users";
		try{
			long userCount = jdbcTemplate.queryForObject(sql, Long.class);
			return userCount;
		}catch(DataAccessException e){
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public long getSystemsCount() {
		String sql = "select count(*) as classification_count from classification";
		try {
			long classificationCount = jdbcTemplate.queryForObject(sql, Long.class);
			return classificationCount;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public long getObservationCount() {
		String sql = "select count(*) as observation_count from information";
		try {
			long observationCount = jdbcTemplate.queryForObject(sql, Long.class);
			return observationCount;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
