package com.meteorology.swat.service;

import java.sql.SQLException;
import java.util.List;

import com.meteorology.swat.DAO.LatLongDAO;
import com.meteorology.swat.DAOImpl.LatLongDAOImpl;
import com.meteorology.swat.bean.LatLongFromDB;
import com.meteorology.swat.model.StartAndEndDates;

public class LatLongService {

	public List<LatLongFromDB> getReferenceLatLong(StartAndEndDates startAndEndDates, String region) throws SQLException{
		
		LatLongDAO latLongDAO = new LatLongDAOImpl();
		latLongDAO.setDataSource();
		List<LatLongFromDB> latLongFromDB = latLongDAO.getReferenceLatLong(startAndEndDates, region);
		return latLongFromDB ;
	}
	
}
