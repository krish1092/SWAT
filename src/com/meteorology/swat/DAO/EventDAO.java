package com.meteorology.swat.DAO;

import java.util.List;

import javax.sql.DataSource;

import com.meteorology.swat.bean.Event;

/**
 * The DAO to interact with event related schema.
 * @author Krishnan Subramanian
 *
 */
public interface EventDAO {
	
	/**
	 * Insert the event into the schema.
	 * @param eventsList
	 */
	public void insert(List<Event> eventsList);
	
	public void setDataSource(DataSource ds);

}
