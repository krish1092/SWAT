package com.meteorology.swat.DAOImpl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.meteorology.swat.DAO.UnmodifiedClassificationDAO;
import com.meteorology.swat.bean.UnmodifiedClassification;
import com.meteorology.swat.util.DBProperties;

public class UnmodifiedClassificationDAOImpl implements UnmodifiedClassificationDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void setDataSource() {
        try {
        	SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
			dataSource.setDriver(new com.mysql.jdbc.Driver());
			dataSource.setUrl(DBProperties.getDbUrl());
        	dataSource.setUsername(DBProperties.getDbUsername());
        	dataSource.setPassword(DBProperties.getDbPassword());
	        this.jdbcTemplate = new JdbcTemplate(dataSource);
			
		} catch (SQLException e) {
			e.printStackTrace();
			this.jdbcTemplate = null;
		}
  }

	@Override
	public void insertIntoUnmodifiedClassification(final List<UnmodifiedClassification> unmodifiedClassificationList) {
		
		

		String[] sql=new String[unmodifiedClassificationList.size()];
		
		for(int i=0; i < unmodifiedClassificationList.size() ; i++)
		{
			 sql[i] = "insert into unmodified_user_classification ( info_id, classification, date_time)"
						+ " values ("+ unmodifiedClassificationList.get(i).getInfoID() +
						" , '" + unmodifiedClassificationList.get(i).getClassification() 
						+"' , '" + unmodifiedClassificationList.get(i).getDateTime()
						+ "')";
		}
		
		try{
			
			for(int i=0;i<sql.length;i++)
			{
				System.out.println(sql[i]);
				this.jdbcTemplate.execute(sql[i]);
			}
			
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void selectFromUnmodifiedClassification() {
		// TODO Auto-generated method stub

	}

}
