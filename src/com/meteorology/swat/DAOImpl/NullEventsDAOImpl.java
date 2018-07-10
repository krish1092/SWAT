package com.meteorology.swat.DAOImpl;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.meteorology.swat.DAO.NullEventsDAO;
import com.meteorology.swat.bean.NullEvents;
import com.meteorology.swat.util.DBProperties;

public class NullEventsDAOImpl implements NullEventsDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void setDataSource() throws SQLException {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new com.mysql.jdbc.Driver());
        dataSource.setUrl(DBProperties.getDbUrl());
    	dataSource.setUsername(DBProperties.getDbUsername());
    	dataSource.setPassword(DBProperties.getDbPassword());
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void insertIntoNullEvents(NullEvents nullEvents){
		
		String sql = "insert into null_events (classification_id,info_id) values (?,?)";
		
		jdbcTemplate.update(sql,nullEvents.getClassificationID(),nullEvents.getInfoID());
		
		
		
	}

}
