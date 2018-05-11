package com.meteorology.swat.rowmapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.jdbc.core.RowMapper;

public class PieChartMapper implements RowMapper<HashMap<String,BigDecimal>>{
	@Override
	public HashMap<String,BigDecimal> mapRow(ResultSet rs, int row) throws SQLException {
		
		ResultSetMetaData metaData = rs.getMetaData();
		int columns = metaData.getColumnCount();
		HashMap<String,BigDecimal> pie= new HashMap<String,BigDecimal>();
		
		
			for(int i=1; i<=columns;i++)
			{
				pie.put(metaData.getColumnName(i),(BigDecimal) rs.getObject(i));
			}
		
		
		return pie;
	}

}


