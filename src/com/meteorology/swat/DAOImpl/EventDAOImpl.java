package com.meteorology.swat.DAOImpl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.meteorology.swat.DAO.EventDAO;
import com.meteorology.swat.bean.Event;


public class EventDAOImpl implements EventDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insert(final List<Event> eventsList) {
		
		
		String[] sql=new String[eventsList.size()];
		
		for( int i=0;i<eventsList.size();i++)
		{
			
			if(eventsList.get(i).getEventType().equalsIgnoreCase("thunderstorm") || eventsList.get(i).getEventType().equalsIgnoreCase("hail"))
			{
				sql[i]="insert into "+eventsList.get(i).getEventType()+" (classification_id,info_id,magnitude,state)"
					+ "values ("+eventsList.get(i).getClassificationId()+","+eventsList.get(i).getInfoId()+","+eventsList.get(i).getMagnitude()+",'"+eventsList.get(i).getState()+"')";
		
			}
			else if(eventsList.get(i).getEventType().equalsIgnoreCase("flashflood"))
			{
				sql[i]="insert into "+eventsList.get(i).getEventType()+" (classification_id,info_id,state)"
						+ "values ("+eventsList.get(i).getClassificationId()+","+eventsList.get(i).getInfoId()+",'"+eventsList.get(i).getState()+"')";;
			
			}
			else if(eventsList.get(i).getEventType().equalsIgnoreCase("tornado"))
			{
				sql[i]="insert into "+eventsList.get(i).getEventType()+" (classification_id,info_id,magnitude,state)"
						+ "values ("+eventsList.get(i).getClassificationId()+","+eventsList.get(i).getInfoId()+",'"+eventsList.get(i).getTornadoFScale()+"'"+",'"+eventsList.get(i).getState()+"')";
			}
		}
		
		/*String sql ="insert into "+eventType+" ("+eventType+"_id,classification_id,info_id,magnitude)"
				+ "values (?,?,?,?)";*/
		try
		{
			//im.getUserSelectedDate()
			//this.jdbcTemplate.update(sql,e.getClassificationId(),e.getInfoId(),e.getMagnitude());
			
			
			//Batch Insert
			for(int i=0;i<sql.length;i++)
				{
					System.out.println(sql[i]);
					this.jdbcTemplate.execute(sql[i]);
				}
			
			
			System.out.println("Successfully inserted into the events tables");
			
			
			
			
		}catch(Exception ex){
			
			ex.printStackTrace();
		
		}
		

	}

	@Override
	public void setDataSource(DataSource ds) {
		
		this.jdbcTemplate=new JdbcTemplate(ds);
		
	}

}
