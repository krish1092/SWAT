
package com.meteorology.swat.junk;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.meteorology.swat.DAO.LatLongDAO;
import com.meteorology.swat.DAOImpl.LatLongDAOImpl;
import com.meteorology.swat.bean.LatLongFromDB;
import com.meteorology.swat.model.StartAndEndDates;
import com.meteorology.swat.util.Utilities;

public class Sample6 {
	
	public static void main(String[] a) throws ParseException, SQLException{
		LatLongDAO latLongDAO = new LatLongDAOImpl();
		String region = "cent_plains";
		StartAndEndDates startAndEndDates = new StartAndEndDates();
		
		
		Utilities utility = new Utilities();
		
		Date startDate = utility.date("12/10/2011 2300");
		Date endDate = utility.date("12/11/2011 1300");
		
		System.out.println(startDate.toString());
		
		
		startAndEndDates.setStartDate(startDate);
		startAndEndDates.setEndDate(endDate);
		latLongDAO.setDataSource();
		List<LatLongFromDB> list =  latLongDAO.getReferenceLatLong(startAndEndDates, region);
		
		for(LatLongFromDB latlong : list){
			System.out.println(latlong.getEastLongitude());
			System.out.println(latlong.getWestLongitude());
			System.out.println(latlong.getSouthLatitude());
			System.out.println(latlong.getNorthLatitude());
			System.out.println(latlong.getReferenceStartDate());
			System.out.println(latlong.getReferenceEndDate());
			System.out.println();
		}
		
	}

}
