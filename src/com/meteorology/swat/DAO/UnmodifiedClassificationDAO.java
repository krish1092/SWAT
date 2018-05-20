package com.meteorology.swat.DAO;

import java.util.List;

import com.meteorology.swat.DAOImpl.UnmodifiedClassificationDAOImpl;
import com.meteorology.swat.bean.UnmodifiedClassification;

/**
 * The DAO to interact with unmodified classification related schema.
 * @author Krishnan Subramanian
 *
 */
public interface UnmodifiedClassificationDAO {

	public void setDataSource();
	
	/**
	 * Insert the unmodified classification into the schema.
	 * @param unmodifiedClassificationList
	 */
	public void insertIntoUnmodifiedClassification(final List<UnmodifiedClassification> unmodifiedClassificationList);
	
	/**
	 * Select data from unmodified table
	 */
	public void selectFromUnmodifiedClassification();
	
	/**
	 * A factory class to get the Implementation.
	 *
	 */
	public static class Factory {
		/**
		 * Create a {@link UnmodifiedClassificationDAO} object.
		 * @return a {@link UnmodifiedClassificationDAO} object.
		 */
		public UnmodifiedClassificationDAO create() {
			return new UnmodifiedClassificationDAOImpl();
		}
	}
}
