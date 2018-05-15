package com.meteorology.swat.service;

import java.math.BigInteger;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.meteorology.swat.DAO.InformationDAO;
import com.meteorology.swat.DAOImpl.InformationDAOImpl;
import com.meteorology.swat.bean.Information;

/**
 * Class to handle information table related operations.
 * @author Krishnan Subramanian
 *
 */
public class InformationService {

	/**
	 * Store the information into the schema.
	 * @param information The {@link Information} object.
	 * @param singleConnectionDataSource The database connection info.
	 * @return The stored information id.
	 */
	public BigInteger storeInInformationTable(Information information,SingleConnectionDataSource singleConnectionDataSource)
	{
		InformationDAO informationDAO = new InformationDAOImpl();
		informationDAO.setDataSource(singleConnectionDataSource);
		BigInteger infoID = informationDAO.insert(information);
		return infoID;
	}
}
