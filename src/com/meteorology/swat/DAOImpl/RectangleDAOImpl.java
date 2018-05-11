package com.meteorology.swat.DAOImpl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.meteorology.swat.DAO.RectangleDAO;
import com.meteorology.swat.bean.Rectangle;



public class RectangleDAOImpl implements RectangleDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Rectangle select(String date, String centreTime) {
		
		String sql="select * from rectangles";
		
		return null;
	}

	@Override
	public void setDataSource(DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
	}

	
	
}
