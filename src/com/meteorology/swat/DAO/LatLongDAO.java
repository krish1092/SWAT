package com.meteorology.swat.DAO;

import java.sql.SQLException;
import java.util.List;

import com.meteorology.swat.bean.LatLongFromDB;
import com.meteorology.swat.model.StartAndEndDates;

/**
 * The DAO to interact with reference latitude longitude related schema.
 * @author krishnan
 *
 */
public interface LatLongDAO {

	public void setDataSource() throws SQLException;
	
	/**
	 * Get the reference latitude and longitude information.
	 * @param startAndEndDates The begin and end dates.
	 * @param region The region to get the latitude and longitude for.
	 * @return The list of latitudes and longitudes.
	 */
	public List<LatLongFromDB> getReferenceLatLong(StartAndEndDates startAndEndDates, String region );
	
}
