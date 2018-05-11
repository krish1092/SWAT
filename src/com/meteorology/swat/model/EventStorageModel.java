package com.meteorology.swat.model;

public class EventStorageModel {

	private double beginLat,beginLong,endLat,endLong;
	private String eventType,date,userClassification,userSessionID,region;
	private int beginTime,timeFromUserImage,endTime;
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	public double getBeginLat() {
		return beginLat;
	}
	public void setBeginLat(double beginLat) {
		this.beginLat = beginLat;
	}
	public double getBeginLong() {
		return beginLong;
	}
	public void setBeginLong(double beginLong) {
		this.beginLong = beginLong;
	}
	public double getEndLat() {
		return endLat;
	}
	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}
	public double getEndLong() {
		return endLong;
	}
	public void setEndLong(double endLong) {
		this.endLong = endLong;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(int beginTime) {
		this.beginTime = beginTime;
	}
	public int getTimeFromUserImage() {
		return timeFromUserImage;
	}
	public void setTimeFromUserImage(int timeFromUserImage) {
		this.timeFromUserImage = timeFromUserImage;
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
	
}
