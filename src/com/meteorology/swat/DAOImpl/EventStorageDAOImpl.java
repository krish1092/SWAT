package com.meteorology.swat.DAOImpl;


import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.meteorology.swat.DAO.EventStorageDAO;
import com.meteorology.swat.model.EventStorageModel;
import com.meteorology.swat.model.RegionAndLocationModel;
import com.meteorology.swat.rowmapper.RegionMapper;


@Repository
public class EventStorageDAOImpl implements EventStorageDAO{

	 private JdbcTemplate jdbcTemplate;
	 
	 
	 
	public void setDataSource(DataSource dataSource) {
		
			this.jdbcTemplate = new JdbcTemplate(dataSource);
	        
	    }
	 
	//Select from the regions table
	 
		 public RegionAndLocationModel selectFromRegions(String region)
		 {
			 RegionAndLocationModel regions=new RegionAndLocationModel();
			 String sql="select * from regions where region=?";
			 
			 try{
				 
				regions=this.jdbcTemplate.queryForObject(sql,new Object[]{region},new RegionMapper());
				
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 
			 return regions;
		 }
	 
	 
	 //Insert into information
	@Override	 
	public void insertIntoInformation(String sq) 
	{
		
		 
		
		String sql ="insert into information (session_id,start_time,end_time,user_selected_date,region)"
				+ "values (?,?,?,?,?)";
		try
		{
			this.jdbcTemplate.update(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	 
	 @Override
	 public void saveOrUpdateInformation(EventStorageModel event) {
	        System.out.println("Going to SQL class----------------");
	        
	        System.out.println(event.getUserSessionID()+event.getTimeFromUserImage()+
					 
					 event.getBeginTime()+ event.getEndTime()+
					 
					 event.getBeginLat()+ event.getBeginLong()+
					 
					 event.getEndLat()+ event.getEndLong()+
					 
		    		 event.getRegion());
	        
	        
	        
		 String sql = "INSERT INTO information (info_id, session_id, user_time, begin_time, end_time, "
		 		+ "begin_lat, begin_long, end_lat, end_long, region)"
                 + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
		 
		 try{
			 jdbcTemplate.update(sql, 
					 
					 null,
					 
					 event.getUserSessionID(),
					 
					 event.getTimeFromUserImage(),
					 
					 event.getBeginTime(), event.getEndTime(),
					 
					 event.getBeginLat(), event.getBeginLong(),
					 
					 event.getEndLat(), event.getEndLong(),
					 
		    		 event.getRegion());
			 
			 System.out.println("Inserted!!!!");
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
	    }
	 
	 
	
	 @Override
	public void saveOrUpdateClassification(EventStorageModel event) {
		
		 String sql = "INSERT INTO classification (class_id, info_id, begin_time, end_time, classification )"
	                 + " VALUES (?, ?, ?, ?, ?)";
		 try{
			 jdbcTemplate.update(sql, 
					 
					 null,
					 
					 1,
					 
					 
					 event.getBeginTime(), event.getEndTime(),
					 
					 event.getUserClassification());
			 
			 System.out.println("Inserted!!!!");
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		
	}
	 
	 
	 
	 
	 
	 
	 
	 public void saveOrUpdateEventStorage(EventStorageModel ed)
	 {
		 
		 //beginLat,beginLong,endLat,endLong, eventType,date,userClassification,userSessionID,region,beginTime,timeFromUserImage,endTime
		 
		 
		 
		 
		 
		 String sql = "INSERT INTO EventStorage (begin_lat, begin_long, end_lat, end_long)"
                 + " VALUES (?, ?, ?, ?)";
		 
		 
		 
		 try
		 {
			 
			 
			 this.jdbcTemplate.update(sql, ed.getBeginLat(), ed.getBeginLong(), ed.getEndLat(), ed.getEndLong());
			 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
	 }
	 
	 
	 
	 
	 
	 
	 /*public List<EventStorageDAO> list() {
		    String sql = "SELECT * FROM contact";
		    List<EventStorageDAO> listContact = jdbcTemplate.query(sql, new RowMapper<EventStorageDAO>() {
		 
		        @Override
		        public EventStorageDAO mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	EventStorageDAO aContact = new EventStorageDAO();
		 
		            aContact.setBeginLat(rs.getInt("beginlat"));
		            aContact.setBeginLong(rs.getInt("beginlong"));
		            aContact.setBeginTime(rs.getInt("begintime"));
		           
		 
		            return aContact;
		        }
		 
		    });
		 
		    return listContact;
		}
	 */
}
