package com.meteorology.swat.DAO;

import java.math.BigInteger;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.meteorology.swat.DAOImpl.InformationDAOImpl;
import com.meteorology.swat.bean.Information;

/**
 * The DAO to interact with information related schema.
 * @author Krishnan Subramanian
 *
 */
public interface InformationDAO {
	
	/**
	 * Insert the information into the schema.
	 * @param im The information to insert.
	 * @return The inserted information id.
	 */
	public BigInteger insert(Information im); 
	
	/**
	 * 
	 * @param ds
	 */
	public void setDataSource(SingleConnectionDataSource ds);
	
	/**
	 * A factory class to get the Implementation.
	 *
	 */
	public static class Factory {
		/**
		 * Create a {@link InformationDAO} object.
		 * @return a {@link InformationDAO} object.
		 */
		public InformationDAO create() {
			return new InformationDAOImpl();
		}
	}
}
