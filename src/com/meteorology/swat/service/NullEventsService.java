package com.meteorology.swat.service;

import java.sql.SQLException;

import com.meteorology.swat.DAO.NullEventsDAO;
import com.meteorology.swat.DAOImpl.NullEventsDAOImpl;
import com.meteorology.swat.bean.NullEvents;

public class NullEventsService {
	
	
	public void insertIntoNullEvents(NullEvents nullEvents) throws SQLException{
		
		
		NullEventsDAO nullEventsDAO= new NullEventsDAOImpl();
		
		nullEventsDAO.setDataSource();
		nullEventsDAO.insertIntoNullEvents(nullEvents);
		
		
	}

}
