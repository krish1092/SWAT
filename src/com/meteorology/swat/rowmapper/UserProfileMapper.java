/*package com.meteorology.swat.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meteorology.swat.bean.UserProfileBean;

public class UserProfileMapper implements RowMapper<UserProfileBean>{

	@Override
	public UserProfileBean mapRow(ResultSet rs, int row) throws SQLException {
		
		UserProfileBean userProfileBean= new UserProfileBean();
		userProfileBean.setClassification(rs.getString("classification"));
		userProfileBean.setInfoID( rs.getLong("info_id"));
		userProfileBean.setStartTime(rs.getString("start_time"));
		userProfileBean.setEndTime(rs.getString("end_time"));
		
		return userProfileBean;
	}
}
*/