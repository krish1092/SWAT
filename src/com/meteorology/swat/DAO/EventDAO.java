package com.meteorology.swat.DAO;

import java.util.List;

import javax.sql.DataSource;

import com.meteorology.swat.bean.Event;

public interface EventDAO {
	
	public void insert(List<Event> eventsList);
	
	public void setDataSource(DataSource ds);

}
