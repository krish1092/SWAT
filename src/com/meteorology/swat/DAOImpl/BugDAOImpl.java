package com.meteorology.swat.DAOImpl;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.meteorology.swat.DAO.BugDAO;
import com.meteorology.swat.bean.Bug;

public class BugDAOImpl implements BugDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void setDataSource() throws SQLException 
	{

		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new com.mysql.jdbc.Driver());
        dataSource.setUrl("jdbc:mysql://localhost:3306/swat");
        dataSource.setUsername("root");
        dataSource.setPassword("SwatTool@2015");
        //dataSource.setPassword("Swat@2016");
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	

	}

	@Override
	public boolean insertBug(Bug bug) 
	{
		String sql = "insert into bugs values(?,?)";
		try
		{
			jdbcTemplate.update(sql, null, bug.getDescription());
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

}
