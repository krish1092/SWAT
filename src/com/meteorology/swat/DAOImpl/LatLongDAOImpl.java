package com.meteorology.swat.DAOImpl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.meteorology.swat.DAO.LatLongDAO;
import com.meteorology.swat.bean.LatLongFromDB;
import com.meteorology.swat.model.StartAndEndDates;
import com.meteorology.swat.rowmapper.LatLongMapper;

public class LatLongDAOImpl implements LatLongDAO {

	private JdbcTemplate jdbcTemplate;
	@Override
	public void setDataSource() throws SQLException {

		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new com.mysql.jdbc.Driver());
        dataSource.setUrl("jdbc:mysql://localhost:3306/swat");
        dataSource.setUsername("root");
        dataSource.setPassword("SwatTool@2015");
        //dataSource.setPassword("Swat@2016");
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	
	}

	@Override
	public List<LatLongFromDB> getReferenceLatLong(StartAndEndDates startAndEndDates, String region) {
		
		Date startDate = startAndEndDates.getStartDate();
		Date endDate = startAndEndDates.getEndDate();
		
		String sql = "select start_date,end_date,region_east_long,region_west_long,region_north_lat,region_south_lat from latlong "
				+ "where region = ? AND "
				+ "(start_date <= ? AND end_date >= ?) OR (start_date <= ? AND end_date >= ?)";
		
		List<LatLongFromDB> listOfLatLongs = jdbcTemplate.query(sql, new Object[]{region,startDate,startDate,endDate,endDate}, new LatLongMapper());
		return listOfLatLongs;
	}

}




/*
String sql = "select region_east_long,region_west_long,region_north_lat,region_south_lat from latlong "
		+ "where region = ? AND start_date <= ? AND end_date >= ?";

List<LatLongFromDB> listOfLatLongs = jdbcTemplate.queryForList(sql, LatLongFromDB.class, new Object[]{region,startDate,endDate});
LatLongFromDB latLongs = jdbcTemplate.queryForObject(sql, new Object[]{region,startDate,endDate}, new LatLongMapper());
listOfLatLongs = new ArrayList<LatLongFromDB>();
if(listOfLatLongs.size() == 0){
	sql = "select region_east_long,region_west_long,region_north_lat,region_south_lat from latlong "
			+ "where region = ? AND "
			+ "(start_date <= ? AND end_date >= ?) OR (start_date <= ? AND end_date >= ?)";
	latLongs = jdbcTemplate.queryForObject(sql, new Object[]{region,startDate,startDate,endDate,endDate}, new LatLongMapper());
	
	listOfLatLongs.add(latLongs);
	
	sql = "select region_east_long,region_west_long,region_north_lat,region_south_lat from latlong where region = ? AND start_date <= ? AND end_date >= ?";
	latLongs = jdbcTemplate.queryForObject(sql, new Object[]{region,endDate,endDate}, new LatLongMapper());
	
	listOfLatLongs.add(latLongs);
	
}else{
	
	listOfLatLongs.add(latLongs);
	
}*/