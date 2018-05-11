package com.meteorology.swat.service;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.meteorology.swat.DAO.UserProfileDAO;
import com.meteorology.swat.DAOImpl.UserProfileDAOImpl;
import com.meteorology.swat.bean.ProfileResult;


public class UserProfileService {
	
	
	public Map< Long, List<ProfileResult> > selectRecent(String userEmail){
		UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
		userProfileDAO.setDataSource();
		Map< Long, List<ProfileResult> > mapWithList = userProfileDAO.selectRecentTen(userEmail);
		return mapWithList;
	}
	
	public Map< DateTime, Integer> getHailCounts(long infoID){
		UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
		userProfileDAO.setDataSource();
		Map<DateTime, Integer> hailCounts = userProfileDAO.getHailCountsForInfoID(infoID);
		return hailCounts;
	}
	
	public Map< DateTime, Integer> getTornadoCounts(long infoID){
		UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
		userProfileDAO.setDataSource();
		Map<DateTime, Integer> tornadoCounts = userProfileDAO.getTornadoCountsForInfoID(infoID);
		return tornadoCounts;
	}
	
	public Map< DateTime, Integer> getThunderstormCounts(long infoID){
		UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
		userProfileDAO.setDataSource();
		Map<DateTime, Integer> thunderstormCounts = userProfileDAO.getThunderstormCountsForInfoID(infoID);
		return thunderstormCounts;
	}
	
	
	public Map< DateTime, Integer> getFlashfloodCounts(long infoID){
		UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
		userProfileDAO.setDataSource();
		Map<DateTime, Integer> flashfloodCounts = userProfileDAO.getFlashfloodCountsForInfoID(infoID);
		return flashfloodCounts;
	}
	
	
	
	
}
