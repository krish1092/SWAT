package com.meteorology.swat.DAO;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.meteorology.swat.bean.ProfileResult;

public interface UserProfileDAO {

	public void setDataSource();

	public Map< Long, List<ProfileResult> > selectRecentTen(String userEmail);

	public Map< DateTime , Integer> getHailCountsForInfoID(long infoID);
	
	public Map< DateTime, Integer> getTornadoCountsForInfoID(long infoID);
	
	public Map< DateTime, Integer> getThunderstormCountsForInfoID(long infoID);
	
	public Map< DateTime, Integer> getFlashfloodCountsForInfoID(long infoID);
	
}
