package com.meteorology.swat.bean;

import java.sql.Timestamp;
import java.util.Date;


//import org.joda.time.DateTime;

public class ProfileResult {
	
	private String classification,region;
	private long hailCount, thunderstormWindCount, flashfloodCount, tornadoCount, nullEventsCount;
	private double westLong, eastLong, northLat, southLat;
	private Date startTime, endTime, centreTime;
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public long getHailCount() {
		return hailCount;
	}
	public void setHailCount(long hailCount) {
		this.hailCount = hailCount;
	}
	public long getThunderstormWindCount() {
		return thunderstormWindCount;
	}
	public void setThunderstormWindCount(long thunderstormWindCount) {
		this.thunderstormWindCount = thunderstormWindCount;
	}
	public long getFlashfloodCount() {
		return flashfloodCount;
	}
	public void setFlashfloodCount(long flashfloodCount) {
		this.flashfloodCount = flashfloodCount;
	}
	public long getTornadoCount() {
		return tornadoCount;
	}
	public void setTornadoCount(long tornadoCount) {
		this.tornadoCount = tornadoCount;
	}
	public long getNullEventsCount() {
		return nullEventsCount;
	}
	public void setNullEventsCount(long nullEventsCount) {
		this.nullEventsCount = nullEventsCount;
	}
	public double getWestLong() {
		return westLong;
	}
	public void setWestLong(double westLong) {
		this.westLong = westLong;
	}
	public double getEastLong() {
		return eastLong;
	}
	public void setEastLong(double eastLong) {
		this.eastLong = eastLong;
	}
	public double getNorthLat() {
		return northLat;
	}
	public void setNorthLat(double northLat) {
		this.northLat = northLat;
	}
	public double getSouthLat() {
		return southLat;
	}
	public void setSouthLat(double southLat) {
		this.southLat = southLat;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp timestamp) {
		this.startTime = timestamp;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp timestamp) {
		this.endTime = timestamp;
	}
	public Date getCentreTime() {
		return centreTime;
	}
	public void setCentreTime(Timestamp timestamp) {
		this.centreTime = timestamp;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
}
