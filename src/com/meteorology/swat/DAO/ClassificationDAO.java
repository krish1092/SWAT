package com.meteorology.swat.DAO;

import java.math.BigInteger;

import javax.sql.DataSource;

import com.meteorology.swat.DAOImpl.ClassificationDAOImpl;
import com.meteorology.swat.bean.Classification;

/**
 * The DAO to interact with classification related schema.
 * @author Krishnan Subramanian
 *
 */
public interface ClassificationDAO {

	/**
	 * Insert the {@link Classification} into the schema.
	 * @param cm The {@link Classification} to insert.
	 * @return The classification id.
	 */
	public BigInteger insert(Classification cm);
	
	/**
	 * The database information.
	 * @param ds
	 */
	public void setDataSource(DataSource ds);
	
	/**
	 * A factory class to get the Implementation.
	 *
	 */
	public static class Factory {
		/**
		 * Create a {@link ClassificationDAO} object.
		 * @return a {@link ClassificationDAO} object.
		 */
		public ClassificationDAO create() {
			return new ClassificationDAOImpl();
		}
	}		
}
