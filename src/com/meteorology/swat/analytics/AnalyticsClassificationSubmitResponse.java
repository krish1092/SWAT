package com.meteorology.swat.analytics;

public class AnalyticsClassificationSubmitResponse {
	
	private String message;
	private ExpertClassification expertClassification;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ExpertClassification getExpertClassification() {
		return expertClassification;
	}
	public void setExpertClassification(ExpertClassification expertClassification) {
		this.expertClassification = expertClassification;
	}
	
	

}
