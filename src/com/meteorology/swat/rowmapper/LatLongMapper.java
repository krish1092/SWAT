package com.meteorology.swat.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meteorology.swat.bean.LatLongFromDB;



public class LatLongMapper implements RowMapper<LatLongFromDB>{

	@Override
	public LatLongFromDB mapRow(ResultSet rs, int row) throws SQLException {
		LatLongFromDB latLongFromDB = new LatLongFromDB();
		latLongFromDB.setEastLongitude(rs.getDouble("region_east_long"));
		latLongFromDB.setWestLongitude(rs.getDouble("region_west_long"));
		latLongFromDB.setNorthLatitude(rs.getDouble("region_north_lat"));
		latLongFromDB.setSouthLatitude(rs.getDouble("region_south_lat"));
		latLongFromDB.setReferenceStartDate(rs.getDate("start_date"));
		latLongFromDB.setReferenceEndDate(rs.getDate("end_date"));
		return latLongFromDB;
	}

}
