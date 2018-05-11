package com.meteorology.swat.DAOImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.meteorology.swat.DAO.PieChartDAO;
import com.meteorology.swat.rowmapper.PieChartMapper;


public class PieChartDAOImpl implements PieChartDAO{

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void setDataSource(DataSource ds) {
		
		jdbcTemplate = new JdbcTemplate(ds);
	}

	@Override
	public HashMap<String, BigDecimal> select() {
		
		HashMap<String, BigDecimal> piechartValues = new HashMap<String,BigDecimal>();
		List<HashMap<String, BigDecimal>> list = new ArrayList<HashMap<String, BigDecimal>>();
		try
		{
			
			String sql="select * from piechart";
			list = jdbcTemplate.query(sql, new PieChartMapper());
			piechartValues = list.get(0);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return piechartValues;
	}

}
