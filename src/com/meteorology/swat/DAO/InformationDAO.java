package com.meteorology.swat.DAO;

import java.math.BigInteger;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.meteorology.swat.bean.Information;



public interface InformationDAO {
	
	public BigInteger insert(Information im); 
	
	public void setDataSource(SingleConnectionDataSource ds);
	
}
