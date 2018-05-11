package com.meteorology.swat.DAO;

import java.sql.SQLException;

import com.meteorology.swat.bean.Bug;

public interface BugDAO {

	public void setDataSource() throws SQLException;
	
	public boolean insertBug(Bug bug);
	
}
