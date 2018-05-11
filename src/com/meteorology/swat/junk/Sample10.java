package com.meteorology.swat.junk;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.meteorology.swat.DAO.UserProfileDAO;
import com.meteorology.swat.DAOImpl.UserProfileDAOImpl;
import com.meteorology.swat.bean.ProfileResult;

public class Sample10 {
	
	public static void main(String[] args) throws SQLException{
		
		UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
		userProfileDAO.setDataSource();
		Map< Long, List<ProfileResult> > mapWithList = userProfileDAO.selectRecentTen("krish@iastate.edu");
		
	}
	

}
