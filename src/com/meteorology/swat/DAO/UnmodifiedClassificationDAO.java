package com.meteorology.swat.DAO;

import java.util.List;

import com.meteorology.swat.bean.UnmodifiedClassification;

/**
 * The DAO to interact with unmodified classification related schema.
 * @author krishnan
 *
 */
public interface UnmodifiedClassificationDAO {

	public void setDataSource();
	
	/**
	 * Insert the unmodified classification into the schema.
	 * @param unmodifiedClassificationList
	 */
	public void insertIntoUnmodifiedClassification(final List<UnmodifiedClassification> unmodifiedClassificationList);
	
	/**
	 * Select data from unmodified table
	 */
	public void selectFromUnmodifiedClassification();
}
