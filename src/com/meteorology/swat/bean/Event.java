package com.meteorology.swat.bean;

import java.math.BigInteger;

public class Event {
	private BigInteger uniqueId,classificationId,infoId;
	private double magnitude;
	private String tornadoFScale,eventType,state;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public BigInteger getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(BigInteger uniqueId) {
		this.uniqueId = uniqueId;
	}
	public BigInteger getClassificationId() {
		return classificationId;
	}
	public void setClassificationId(BigInteger classificationId) {
		this.classificationId = classificationId;
	}
	public BigInteger getInfoId() {
		return infoId;
	}
	public void setInfoId(BigInteger infoId) {
		this.infoId = infoId;
	}
	public double getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}
	public String getTornadoFScale() {
		return tornadoFScale;
	}
	public void setTornadoFScale(String tornadoFScale) {
		this.tornadoFScale = tornadoFScale;
	}
	
	
}
