package com.meteorology.swat.analytics;

import java.sql.Timestamp;
import java.util.Date;

public class AnalyticsReportBean {

	private long count;
	private Date dateTime;
	private String region;
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp timestamp) {
		this.dateTime = timestamp;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
}
