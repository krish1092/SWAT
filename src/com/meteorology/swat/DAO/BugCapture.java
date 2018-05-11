package com.meteorology.swat.DAO;

import java.sql.SQLException;

import com.meteorology.swat.bean.Bug;

public interface BugCapture {
	
	public void insertBug(Bug b);
	
	public void setDataSource() throws SQLException;

}
