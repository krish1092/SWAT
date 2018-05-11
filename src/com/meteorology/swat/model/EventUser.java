package com.meteorology.swat.model;

import java.util.HashMap;

public class EventUser {

	private double topLatFromUser,bottomLatFromUser,leftLongFromUser,rightLongFromUser;
	private String userClassification,userSessionID,region;
	private String beginTime,endTime;
	
	private HashMap<String, StartAndEndDates> map;
	
	
	
	
	public HashMap<String, StartAndEndDates> getMap() {
		return map;
	}
	public void setMap(HashMap<String, StartAndEndDates> map) {
		this.map = map;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public double getTopLatFromUser() {
		return topLatFromUser;
	}
	public void setTopLatFromUser(double topLatFromUser) {
		this.topLatFromUser = topLatFromUser;
	}
	public double getBottomLatFromUser() {
		return bottomLatFromUser;
	}
	public void setBottomLatFromUser(double bottomLatFromUser) {
		this.bottomLatFromUser = bottomLatFromUser;
	}
	public double getLeftLongFromUser() {
		return leftLongFromUser;
	}
	public void setLeftLongFromUser(double leftLongFromUser) {
		this.leftLongFromUser = leftLongFromUser;
	}
	public double getRightLongFromUser() {
		return rightLongFromUser;
	}
	public void setRightLongFromUser(double rightLongFromUser) {
		this.rightLongFromUser = rightLongFromUser;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getUserClassification() {
		return userClassification;
	}
	public void setUserClassification(String userClassification) {
		this.userClassification = userClassification;
	}
	public String getUserSessionID() {
		return userSessionID;
	}
	public void setUserSessionID(String userSessionID) {
		this.userSessionID = userSessionID;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
