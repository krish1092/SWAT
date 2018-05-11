package com.meteorology.swat.DAO;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.sql.DataSource;

public interface PieChartDAO {
	
	public void setDataSource(DataSource ds);
	
	public HashMap<String, BigDecimal> select();
	
	
	
}
