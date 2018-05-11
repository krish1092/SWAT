package com.meteorology.swat.DAOImpl;

import java.math.BigInteger;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import com.meteorology.swat.DAO.ClassificationDAO;
import com.meteorology.swat.bean.Classification;


public class ClassificationDAOImpl implements ClassificationDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public BigInteger insert(Classification classification) {
		String sql ="insert into classification (info_id,classification,count)"
				+ "values (?,?,?)";
		try
		{
			//im.getUserSelectedDate()
			this.jdbcTemplate.update(sql,classification.getInfoID(),classification.getClassification(),classification.getCount());
			System.out.println("Successfully inserted into the classfication table");
		}catch(Exception e){
			
			e.printStackTrace();
		
		}
		sql="SELECT last_insert_id()";
		return BigInteger.valueOf(this.jdbcTemplate.queryForObject(sql,Integer.class));
	}

	@Override
	
	public void setDataSource(DataSource ds) {
		
		this.jdbcTemplate=new JdbcTemplate(ds);
	}

}
