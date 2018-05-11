package com.meteorology.swat.service;

import com.meteorology.swat.DAO.FrontPageDAO;
import com.meteorology.swat.DAOImpl.FrontPageDAOImpl;
import com.meteorology.swat.bean.FrontPageDetailsBean;

public class FrontPageService {

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
