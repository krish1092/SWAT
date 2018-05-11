package com.meteorology.swat.bean;

import java.math.BigInteger;

public class Information {
	
	private BigInteger infoID;
	private String startDateTime, endDateTime, centreImageTime;
	private String sessionID,region,emailAddress;
	private double rectWestLong,rectEastLong,rectSouthLat,rectNorthLat;
	
	
	
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public double getRectWestLong() {
		return rectWestLong;
	}
	public void setRectWestLong(double rectWestLong) {
		this.rectWestLong = rectWestLong;
	}
	public double getRectEastLong() {
		return rectEastLong;
	}
	public void setRectEastLong(double rectEastLong) {
		this.rectEastLong = rectEastLong;
	}
	public double getRectSouthLat() {
		return rectSouthLat;
	}
	public void setRectSouthLat(double rectSouthLat) {
		this.rectSouthLat = rectSouthLat;
	}
	public double getRectNorthLat() {
		return rectNorthLat;
	}
	public void setRectNorthLat(double rectNorthLat) {
		this.rectNorthLat = rectNorthLat;
	}
	public BigInteger getInfoID() {
		return infoID;
	}
	public void setInfoID(BigInteger infoID) {
		this.infoID = infoID;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCentreImageTime() {
		return centreImageTime;
	}
	public void setCentreImageTime(String centreImageTime) {
		this.centreImageTime = centreImageTime;
	}
	
	
}
