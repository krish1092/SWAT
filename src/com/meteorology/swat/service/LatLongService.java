package com.meteorology.swat.service;

import java.sql.SQLException;
import java.util.List;

import com.meteorology.swat.DAO.LatLongDAO;
import com.meteorology.swat.DAOImpl.LatLongDAOImpl;
import com.meteorology.swat.bean.LatLongFromDB;
import com.meteorology.swat.model.StartAndEndDates;

public class LatLongService {

	/**
	 * Get the reference latitude and longitude data.
	 * @param startAndEndDates The start and end dates.
	 * 			This is required because, the NCDC image drastically changes based the dates.
	 * @param region The region to get the latitudes and longitudes for.
	 * @return The list of latitudes and longitudes for the given region and start and end dates.
	 * @throws SQLException When there is an issue connecting to the database.
	 */
	public List<LatLongFromDB> getReferenceLatLong(StartAndEndDates startAndEndDates, String region) throws SQLException{
		
		LatLongDAO latLongDAO = new LatLongDAOImpl();
		latLongDAO.setDataSource();
		List<LatLongFromDB> latLongFromDB = latLongDAO.getReferenceLatLong(startAndEndDates, region);
		return latLongFromDB ;
	}
	
}
