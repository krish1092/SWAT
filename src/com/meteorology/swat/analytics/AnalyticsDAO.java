package com.meteorology.swat.analytics;

import java.util.List;
import java.util.Map;

public interface AnalyticsDAO {
	
	public void setDataSource();
	
	public List<Map<String, Object>> getCountsOfDateTime(AnalyticsFilter analyticsFilter);
	
	public boolean insertExpertClassification(AnalyticsClassificationBean analyticsClassificationBean);

	public List<AnalyticsReportBean> getOverallAnalyticsReport();
	
	public List<AnalyticsClassificationBean> getClassificationAndUserForGivenDate(String date,String region);
	
	public List<ExpertClassification> getExpertClassificationForGivenDateAndRegion(String dateTime, String region);
	
	public boolean insertIntoExpertClassification(ExpertClassification expertClassification);
}
