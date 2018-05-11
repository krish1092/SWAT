package com.meteorology.swat.service;

import java.math.BigInteger;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.meteorology.swat.DAO.InformationDAO;
import com.meteorology.swat.DAOImpl.InformationDAOImpl;
import com.meteorology.swat.bean.Information;

public class InformationService {

	public BigInteger storeInInformationTable(Information information,SingleConnectionDataSource singleConnectionDataSource)
	{
		InformationDAO informationDAO = new InformationDAOImpl();
		informationDAO.setDataSource(singleConnectionDataSource);
		BigInteger infoID = informationDAO.insert(information);
		return infoID;
	}
	
	
}
