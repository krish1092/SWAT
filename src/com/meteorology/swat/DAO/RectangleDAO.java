package com.meteorology.swat.DAO;

import javax.sql.DataSource;

import com.meteorology.swat.bean.Rectangle;


public interface RectangleDAO {

	public Rectangle select(String date,String centreTime);
	
	public void setDataSource(DataSource ds);
}
