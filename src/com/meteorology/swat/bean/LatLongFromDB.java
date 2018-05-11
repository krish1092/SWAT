package com.meteorology.swat.bean;

import java.util.Date;

public class LatLongFromDB {
	
	
	private double westLongitude,eastLongitude;
	private double northLatitude,southLatitude;
	private Date referenceStartDate, referenceEndDate;
	public Date getReferenceStartDate() {
		return referenceStartDate;
	}
	public void setReferenceStartDate(Date referenceStartDate) {
		this.referenceStartDate = referenceStartDate;
	}
	public Date getReferenceEndDate() {
		return referenceEndDate;
	}
	public void setReferenceEndDate(Date referenceEndDate) {
		this.referenceEndDate = referenceEndDate;
	}
	public double getWestLongitude() {
		return westLongitude;
	}
	public void setWestLongitude(double westLongitude) {
		this.westLongitude = westLongitude;
	}
	public double getEastLongitude() {
		return eastLongitude;
	}
	public void setEastLongitude(double eastLongitude) {
		this.eastLongitude = eastLongitude;
	}
	public double getNorthLatitude() {
		return northLatitude;
	}
	public void setNorthLatitude(double northLatitude) {
		this.northLatitude = northLatitude;
	}
	public double getSouthLatitude() {
		return southLatitude;
	}
	public void setSouthLatitude(double southLatitude) {
		this.southLatitude = southLatitude;
	}
	
	
}
