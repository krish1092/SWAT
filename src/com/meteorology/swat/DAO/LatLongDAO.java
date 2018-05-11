package com.meteorology.swat.DAO;

import java.sql.SQLException;
import java.util.List;

import com.meteorology.swat.bean.LatLongFromDB;
import com.meteorology.swat.model.StartAndEndDates;

public interface LatLongDAO {

	public void setDataSource() throws SQLException;
	
	public List<LatLongFromDB> getReferenceLatLong(StartAndEndDates startAndEndDates, String region );
	
}
