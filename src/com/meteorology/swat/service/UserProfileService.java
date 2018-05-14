package com.meteorology.swat.service;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.meteorology.swat.DAO.UserProfileDAO;
import com.meteorology.swat.DAOImpl.UserProfileDAOImpl;
import com.meteorology.swat.bean.ProfileResult;

/**
 * A service class to handle the user profile related operations.
 * @author Krishnan Subramanian
 *
 */
public class UserProfileService {
	
	/**
	 * Get the most recent 10 classifications of the user.
	 * @param userEmail The email address of the user.
	 * @return A map of information id and its corresponding {@link ProfileResult}
	 */
	public Map< Long, List<ProfileResult> > selectRecent(String userEmail){
		UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
		userProfileDAO.setDataSource();
		Map< Long, List<ProfileResult> > mapWithList = userProfileDAO.selectRecentTen(userEmail);
		return mapWithList;
	}
	
	/**
	 * Get the hail counts.
	 * @param infoID The given information id.
	 * @return A map of dateTime and their corresponding hail counts.
	 */
	public Map< DateTime, Integer> getHailCounts(long infoID){
		UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
		userProfileDAO.setDataSource();
		Map<DateTime, Integer> hailCounts = userProfileDAO.getHailCountsForInfoID(infoID);
		return hailCounts;
	}
	
	/**
	 * Get the tornado counts.
	 * @param infoID The given information id.
	 * @return A map of dateTime and their corresponding tornado counts.
	 */
	public Map< DateTime, Integer> getTornadoCounts(long infoID){
		UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
		userProfileDAO.setDataSource();
		Map<DateTime, Integer> tornadoCounts = userProfileDAO.getTornadoCountsForInfoID(infoID);
		return tornadoCounts;
	}
	
	/**
	 * Get the thunderstorm wind counts.
	 * @param infoID The given information id.
	 * @return A map of dateTime and their corresponding thunderstorm wind counts.
	 */
	public Map< DateTime, Integer> getThunderstormCounts(long infoID){
		UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
		userProfileDAO.setDataSource();
		Map<DateTime, Integer> thunderstormCounts = userProfileDAO.getThunderstormCountsForInfoID(infoID);
		return thunderstormCounts;
	}
	
	/**
	 * Get the flashflood counts.
	 * @param infoID The given information id.
	 * @return A map of dateTime and their corresponding flashflood counts.
	 */
	public Map< DateTime, Integer> getFlashfloodCounts(long infoID){
		UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
		userProfileDAO.setDataSource();
		Map<DateTime, Integer> flashfloodCounts = userProfileDAO.getFlashfloodCountsForInfoID(infoID);
		return flashfloodCounts;
	}
}
