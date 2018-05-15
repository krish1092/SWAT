package com.meteorology.swat.DAO;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.meteorology.swat.bean.ProfileResult;

/**
 * The DAO to interact with user profile related schema.
 * @author krishnan
 *
 */
public interface UserProfileDAO {

	public void setDataSource();

	/**
	 * Select the most recent ten classifications made by the user.
	 * @param userEmail The user account to fetch the values for.
	 * @return The recent 10 results for the user.
	 */
	public Map< Long, List<ProfileResult> > selectRecentTen(String userEmail);

	/**
	 * Get the hail counts for the info id.
	 * @param infoID The information id.
	 * @return A map of dateTime and its corresponding hail count.
	 */
	public Map< DateTime , Integer> getHailCountsForInfoID(long infoID);
	
	/**
	 * Get the tornado counts for the info id.
	 * @param infoID The information id.
	 * @return A map of dateTime and its corresponding tornado count.
	 */
	public Map< DateTime, Integer> getTornadoCountsForInfoID(long infoID);
	
	/**
	 * Get the thunderstorm wind counts for the info id.
	 * @param infoID The information id.
	 * @return A map of dateTime and its corresponding thunderstorm wind count.
	 */
	public Map< DateTime, Integer> getThunderstormCountsForInfoID(long infoID);
	
	/**
	 * Get the flashflood counts for the info id.
	 * @param infoID The information id.
	 * @return A map of dateTime and its corresponding flashflood count.
	 */
	public Map< DateTime, Integer> getFlashfloodCountsForInfoID(long infoID);
	
}
