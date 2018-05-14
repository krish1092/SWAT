package com.meteorology.swat.service;

import com.meteorology.swat.DAO.FrontPageDAO;
import com.meteorology.swat.DAOImpl.FrontPageDAOImpl;
import com.meteorology.swat.bean.FrontPageDetailsBean;

/**
 * A class to handle the count of users, classifications to be shown on the home page
 * of the application.
 * @author Krishnan Subramanian
 *
 */
public class FrontPageService {

	/**
	 * Get the users, observation and systems count.
	 * @return
	 */
	public FrontPageDetailsBean getFrontPageDetails(){
		
		FrontPageDetailsBean frontPageDetailsBean = new FrontPageDetailsBean();
		FrontPageDAO frontPageDAO = new FrontPageDAOImpl();
		
		frontPageDAO.setDataSource();
		frontPageDetailsBean.setUserCount(frontPageDAO.getUserCount());
		frontPageDetailsBean.setObservationCount(frontPageDAO.getObservationCount());
		frontPageDetailsBean.setSystemsCount(frontPageDAO.getSystemsCount());
		
		return frontPageDetailsBean;
	}
}
