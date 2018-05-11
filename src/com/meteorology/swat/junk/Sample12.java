package com.meteorology.swat.junk;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.meteorology.swat.DAO.UserProfileDAO;
import com.meteorology.swat.DAOImpl.CountsForInfoIDResultSetExtractor;
import com.meteorology.swat.DAOImpl.UserProfileDAOImpl;

public class Sample12 {

	public static void main(String[] args) {
		Sample12 u = new Sample12();
		u.setDataSource();
		Map<DateTime, Integer> map = u.getHailCountsForInfoID(23);
		for(DateTime d: map.keySet()){
			System.out.println(d + " : " + map.get(d) );
		}
	}
	
	
	
	
	
	
	private JdbcTemplate jdbcTemplate;
	
	
	public void setDataSource() {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        
		try{
			dataSource.setDriver(new com.mysql.jdbc.Driver());
			dataSource.setUrl("jdbc:mysql://localhost:3306/swat");
	        dataSource.setUsername("root");
	        dataSource.setPassword("Swat@2016");
	        this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
		catch(SQLException e)
		{
			this.jdbcTemplate = null;
		}
        
	}
	
	
	
	
	
public Map<DateTime, Integer> getHailCountsForInfoID(long infoID) {
		
		String sql = "select hail.radar_UTC_time, count(hail.radar_UTC_time) as count from hail"
				+ " where hail.info_id = ?"
				+ " group by hail.radar_UTC_time"
				+ " order by hail.hail_id";
		
		try{
			Map<DateTime, Integer> map = jdbcTemplate.query(sql,new CountsForInfoIDResultExtractor(), infoID);
			return map;
		}
		catch(NullPointerException | DataAccessException e){
			return null;
		}
		
	}
	
	
	public class CountsForInfoIDResultExtractor implements ResultSetExtractor<Map<DateTime, Integer>> {

		@Override
		public Map<DateTime, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			Map<DateTime, Integer> countMap = new HashMap<DateTime, Integer>();
			int count;
			DateTimeZone.setDefault(DateTimeZone.UTC);
			DateTime dateTime;
			while(rs.next()){
				count = rs.getInt("count");
				dateTime = new DateTime ( rs.getTimestamp("radar_UTC_time"));
				countMap.put(dateTime, count);
			}
			return countMap;
		}

}
	
}


