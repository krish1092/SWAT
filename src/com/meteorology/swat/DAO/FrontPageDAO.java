package com.meteorology.swat.DAO;

import com.meteorology.swat.DAOImpl.FrontPageDAOImpl;

/**
 * The DAO to interact with front page related schema.
 * @author Krishnan Subramanian
 *
 */
public interface FrontPageDAO {
	
	public void setDataSource();
	
	/**
	 * Get user count.
	 * @return The count of users.
	 */
	public long getUserCount();
	
	/**
	 * Get the number of observations made.
	 * @return The count of observations.
	 */
	public long getObservationCount();
	
	/**
	 * Get the count of systems.
	 * @return The system count.
	 */
	public long getSystemsCount();
	
	/**
	 * A factory class to get the Implementation.
	 *
	 */
	public static class Factory {
		/**
		 * Create a {@link FrontPageDAO} object.
		 * @return a {@link FrontPageDAO} object.
		 */
		public FrontPageDAO create() {
			return new FrontPageDAOImpl();
		}
	}
}
