package com.meteorology.swat.DAO;

import java.sql.SQLException;

import com.meteorology.swat.bean.NullEvents;

public interface NullEventsDAO {

	
	public void setDataSource() throws SQLException;
	
	public void insertIntoNullEvents(NullEvents nullEvents);
	
}
