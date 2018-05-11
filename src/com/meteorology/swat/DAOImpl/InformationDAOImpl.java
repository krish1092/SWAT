package com.meteorology.swat.DAOImpl;

import java.math.BigInteger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.transaction.annotation.Transactional;

import com.meteorology.swat.DAO.InformationDAO;
import com.meteorology.swat.bean.Information;



public class InformationDAOImpl implements InformationDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void setDataSource(SingleConnectionDataSource ds) {
		
		this.jdbcTemplate = new JdbcTemplate(ds);
		
	} 
	 
	@Override
	@Transactional
	public BigInteger insert(Information information) {
		String sql ="insert into information "
				+ "(session_id,email_address,start_time,end_time,"
				+ "rect_west_long,rect_east_long,rect_south_lat,rect_north_lat,centre_img_time,region)"
				+ "values (?,?,?,?,?,?,?,?,?,?)";
		try
		{
			
			this.jdbcTemplate.update(sql,information.getSessionID(),information.getEmailAddress(),information.getStartDateTime(),information.getEndDateTime()
					,information.getRectWestLong(),information.getRectEastLong(),information.getRectSouthLat(),information.getRectNorthLat(),
					information.getCentreImageTime(),information.getRegion());
			System.out.println("Successfully inserted into the information table");
		}catch(Exception e){
			
			e.printStackTrace();
			return null;
		
		}
		sql="SELECT last_insert_id()";
		return this.jdbcTemplate.queryForObject(sql,BigInteger.class);
		
	}

}
