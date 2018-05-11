package com.meteorology.swat.DAO;

import java.math.BigInteger;

import javax.sql.DataSource;

import com.meteorology.swat.bean.Classification;

public interface ClassificationDAO {

	public BigInteger insert(Classification cm);
	
	public void setDataSource(DataSource ds);
		
}
