package com.meteorology.swat.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.meteorology.swat.bean.ProfileResult;

public class ProfileResultResultSetExtractor implements ResultSetExtractor<Map< Long, List<ProfileResult> >>{
	
	@Override
	public Map< Long, List<ProfileResult> > extractData(ResultSet rs) throws SQLException, DataAccessException {
		ProfileResult profileResult;
		long infoID ;
		Map< Long, List<ProfileResult> > mapWithList = new HashMap<>();
		List<ProfileResult> tempProfileResultList = new ArrayList<ProfileResult>();
		
		//Temp variable for infinite loop check
		int i = 0;
		while(rs.next() && i < 100) 
		{
			
			System.out.println("info_id from database:"+rs.getLong("info_id"));
			profileResult = new ProfileResult();
			profileResult.setClassification(rs.getString("classification"));
			
			
			profileResult.setHailCount(rs.getLong("hail_count"));
			profileResult.setThunderstormWindCount(rs.getLong("thunderstorm_wind_count"));
			profileResult.setFlashfloodCount(rs.getLong("flashflood_count"));
			profileResult.setTornadoCount(rs.getLong("tornado_count"));
			profileResult.setNullEventsCount(rs.getLong("null_count"));
			
			profileResult.setStartTime((rs.getTimestamp("start_time")));
			profileResult.setEndTime((rs.getTimestamp("end_time")));
			profileResult.setCentreTime(rs.getTimestamp("centre_img_time"));
			profileResult.setRegion(rs.getString("region"));
			
			infoID = rs.getLong("info_id");
			
			/*if(mapWithList.containsKey(infoID))
				tempProfileResultList = mapWithList.get(infoID);
			else
				tempProfileResultList = new ArrayList<ProfileResult>();*/
			
			
			if(!mapWithList.containsKey(infoID))
				tempProfileResultList = new ArrayList<ProfileResult>();
			
			tempProfileResultList.add(profileResult);
			mapWithList.put(infoID,tempProfileResultList);
			
			i++;
		}
		
		return mapWithList;
	}

}
