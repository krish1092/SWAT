package com.meteorology.swat.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.meteorology.swat.model.RegionAndLocationModel;

public class RegionMapper implements RowMapper<RegionAndLocationModel>{

	@Override
	public RegionAndLocationModel mapRow(ResultSet rs, int row) throws SQLException {
		
		
		RegionAndLocationModel ralm= new RegionAndLocationModel();
		ralm.setRegion(rs.getString("region"));
		ralm.setStates(rs.getString("states").split(","));
		return ralm;
	}

}
