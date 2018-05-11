package com.meteorology.swat.DAOImpl;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.meteorology.swat.DAO.NullEventsDAO;
import com.meteorology.swat.bean.NullEvents;

public class NullEventsDAOImpl implements NullEventsDAO {
	
	private JdbcTemplate jdbcTemplate;
	
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
	
	@Override
	public void insertIntoNullEvents(NullEvents nullEvents){
		
		String sql = "insert into null_events (classification_id,info_id) values (?,?)";
		
		jdbcTemplate.update(sql,nullEvents.getClassificationID(),nullEvents.getInfoID());
		
		
		
	}

}
