package com.meteorology.swat.DAO;

import java.util.List;

import com.meteorology.swat.bean.UnmodifiedClassification;

public interface UnmodifiedClassificationDAO {

	public void setDataSource();
	
	public void insertIntoUnmodifiedClassification(final List<UnmodifiedClassification> unmodifiedClassificationList);
	
	public void selectFromUnmodifiedClassification();
}
