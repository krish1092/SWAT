package com.meteorology.swat.bean;

import java.math.BigInteger;

public class Classification {
	private BigInteger classificationID,infoID;
	private String classification,startTime,endTime;
	private int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public BigInteger getClassificationID() {
		return classificationID;
	}
	public void setClassificationID(BigInteger classification_id) {
		this.classificationID = classification_id;
	}
	public BigInteger getInfoID() {
		return infoID;
	}
	public void setInfoID(BigInteger info_id) {
		this.infoID = info_id;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
