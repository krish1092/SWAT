package com.meteorology.swat.DAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class CountsForInfoIDResultSetExtractor implements ResultSetExtractor<Map<DateTime, Integer>> {

	@Override
	public Map<DateTime, Integer> extractData(ResultSet rs) {
		
		try{
			Map<DateTime, Integer> countMap = new HashMap<DateTime, Integer>();
			int count;
			DateTime dateTime;
			while(rs.next()){
				count = rs.getInt("count");
				dateTime = new DateTime ( rs.getTimestamp("radar_UTC_time"), DateTimeZone.UTC);
				countMap.put(dateTime, count);
			}
			return countMap;
		}catch( SQLException | DataAccessException e){
			e.printStackTrace();
			return null;
		}
		
	}

}
