package com.meteorology.swat.DAOImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.meteorology.swat.DAO.UserProfileDAO;
import com.meteorology.swat.bean.ProfileResult;
import com.meteorology.swat.rowmapper.ProfileResultResultSetExtractor;
import com.meteorology.swat.util.DBProperties;

public class UserProfileDAOImpl implements UserProfileDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void setDataSource() {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        
		try{
			dataSource.setDriver(new com.mysql.jdbc.Driver());
			dataSource.setUrl(DBProperties.getDbUrl());
        	dataSource.setUsername(DBProperties.getDbUsername());
        	dataSource.setPassword(DBProperties.getDbPassword());
	        this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
		catch(SQLException e)
		{
			this.jdbcTemplate = null;
		}
        
	}
	
	@Override
	public Map< Long, List<ProfileResult> > selectRecentTen(String userEmail){
		
		String sql = "select"
				+ " t1.info_id, t1.start_time, t1.end_time, t1.centre_img_time,t1.region,"
				+ " t2.classification,"
				+ " coalesce(t3.hail_count,0) as hail_count, coalesce(t4.thunderstorm_wind_count,0) as thunderstorm_wind_count,"
				+ " coalesce(t5.flashflood_count,0) as flashflood_count, coalesce(t6.tornado_count,0) as tornado_count,"
				+ " coalesce(t7.null_count,0) as null_count"
				+ " from"
				+ " ("
				+ " select information.info_id, information.start_time, information.end_time , information.centre_img_time , information.region"
				+ "	from information"
				+ "	where information.email_address = ? "
				+ " order by info_id desc"
				+ " limit 5"
				+ " ) t1"
				+ " left outer join"
				+ " ("
				+ " select * from classification"
				+ " ) t2"
				+ " on t1.info_id = t2.info_id"
				+ " left outer join"
				+ " ("
				+ " select info_id, count(*) as hail_count,classification_id from hail group by hail.classification_id"
				+ " ) t3"
				+ " on t1.info_id = t3.info_id and t2.classification_id = t3.classification_id"
				+ " left outer join"
				+ " ("
				+ "	select info_id, count(*) as thunderstorm_wind_count,classification_id from thunderstorm group by thunderstorm.classification_id"
				+ " ) t4"
				+ " on t1.info_id = t4.info_id and t2.classification_id = t4.classification_id"
				+ " left outer join"
				+ " ("
				+ " select info_id, count(*) as flashflood_count,classification_id from flashflood group by flashflood.classification_id"
				+ " ) t5"
				+ " on t1.info_id = t5.info_id and t2.classification_id = t5.classification_id"
				+ " left outer join"
				+ " ("
				+ " select info_id, count(*) as tornado_count,classification_id from tornado group by tornado.classification_id"
				+ " ) t6"
				+ " on t1.info_id = t6.info_id and t2.classification_id = t6.classification_id"
				+ " left outer join"
				+ " ("
				+ " select info_id, count(*) as null_count,classification_id from classification"
				+ " where"
				+ " ("
				+ " classification_id not in (select classification_id from hail)"
				+ " and"
				+ " classification_id not in (select classification_id from flashflood)"
				+ " and"
				+ " classification_id not in (select classification_id from thunderstorm)"
				+ " and"
				+ " classification_id not in (select classification_id from tornado)"
				+ " )"
				+ " group by classification.classification_id"
				+ ") t7"
				+ " on t1.info_id = t7.info_id and t2.classification_id = t7.classification_id"
				+ " order by t1.info_id";
		
		try{
			Map< Long, List<ProfileResult> > mapWithList =	jdbcTemplate.query(sql, new ProfileResultResultSetExtractor(),new Object[] {userEmail});
		 return mapWithList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	
	}
	
	
	@Override
	public Map<DateTime, Integer> getFlashfloodCountsForInfoID(long infoID) {
		String sql = "select flashflood.radar_UTC_time, count(flashflood.radar_UTC_time) as count from flashflood"
				+ " where flashflood.info_id = ?"
				+ " group by flashflood.radar_UTC_time"
				+ " order by flashflood.flashflood_id";
		try{
			Map<DateTime, Integer> map = jdbcTemplate.query(sql, new CountsForInfoIDResultSetExtractor(), infoID);
			return map;
		}
		catch(NullPointerException | DataAccessException e){
			return null;
		}
		
	}
	
	@Override
	public Map<DateTime, Integer> getHailCountsForInfoID(long infoID) {
		
		String sql = "select hail.radar_UTC_time, count(hail.radar_UTC_time) as count from hail"
				+ " where hail.info_id = ?"
				+ " group by hail.radar_UTC_time"
				+ " order by hail.hail_id";
		
		try{
			Map<DateTime, Integer> map = jdbcTemplate.query(sql,new CountsForInfoIDResultSetExtractor(), infoID);
			return map;
		}
		catch(NullPointerException | DataAccessException e){
			return null;
		}
		
	}
	
	@Override
	public Map<DateTime, Integer> getThunderstormCountsForInfoID(long infoID) {
		String sql = "select thunderstorm.radar_UTC_time, count(thunderstorm.radar_UTC_time) as count from thunderstorm"
				+ " where thunderstorm.info_id = ?"
				+ " group by thunderstorm.radar_UTC_time"
				+ " order by thunderstorm.thunderstorm_id;";
		try{
			Map<DateTime, Integer> map = jdbcTemplate.query(sql, new CountsForInfoIDResultSetExtractor(), infoID);
			return map;
		}
		catch(NullPointerException | DataAccessException e){
			return null;
		}
		
	}
	
	@Override
	public Map<DateTime, Integer> getTornadoCountsForInfoID(long infoID) {
		String sql = "select tornado.radar_UTC_time, count(tornado.radar_UTC_time) as count from tornado"
				+ " where tornado.info_id = ?"
				+ " group by tornado.radar_UTC_time"
				+ " order by tornado.tornado_id;";
		try{
			Map<DateTime, Integer> map = jdbcTemplate.query(sql, new CountsForInfoIDResultSetExtractor(), infoID);
			return map;
		}
		catch(NullPointerException | DataAccessException e){
			return null;
		}
		
	}
	
	
	
}
