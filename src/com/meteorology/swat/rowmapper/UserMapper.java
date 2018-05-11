package com.meteorology.swat.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meteorology.swat.bean.UserDetails;

public class UserMapper implements RowMapper<UserDetails>{

	@Override
	public UserDetails mapRow(ResultSet rs, int row) throws SQLException {
		
		UserDetails userDetails = new UserDetails(); 
		userDetails.setPassword(rs.getString("password"));
		userDetails.setName(rs.getString("name"));
		return userDetails;
	}
	

}
