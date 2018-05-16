package com.meteorology.swat.model;

/**
 * Filter to analyze the results.
 * @author Krishnan Subramanian
 *
 */
public class FilterForResult {
	
	private String fromDate, toDate;
	private String state, month;
	
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	

}
