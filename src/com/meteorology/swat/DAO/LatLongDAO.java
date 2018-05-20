package com.meteorology.swat.DAO;

import java.sql.SQLException;
import java.util.List;

import com.meteorology.swat.bean.LatLongFromDB;
import com.meteorology.swat.DAOImpl.LatLongDAOImpl;
import com.meteorology.swat.model.StartAndEndDates;

/**
 * The DAO to interact with reference latitude longitude related schema.
 * @author Krishnan Subramanian
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
	public List<LatLongFromDB> getReferenceLatLong(
			StartAndEndDates startAndEndDates, String region );
	
	/**
	 * A factory class to get the Implementation.
	 *
	 */
	public static class Factory {
		/**
		 * Create a {@link LatLongDAO} object.
		 * @return a {@link LatLongDAO} object.
		 */
		public LatLongDAO create() {
			return new LatLongDAOImpl();
		}
	}
}
