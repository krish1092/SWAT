package com.meteorology.swat.DAO;

import javax.sql.DataSource;

import com.meteorology.swat.model.EventStorageModel;
import com.meteorology.swat.model.RegionAndLocationModel;

public interface EventStorageDAO {

	public void saveOrUpdateInformation(EventStorageModel event);
	
	public void saveOrUpdateClassification(EventStorageModel event);
	
	public void setDataSource(DataSource ds);
	
	public void saveOrUpdateEventStorage(EventStorageModel ed);
	
	public RegionAndLocationModel selectFromRegions(String region);
	
	public void insertIntoInformation(String sql);
	
}
