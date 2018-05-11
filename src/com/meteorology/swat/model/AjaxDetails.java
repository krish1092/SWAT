package com.meteorology.swat.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.TreeMap;

import org.json.JSONObject;

public class AjaxDetails implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6976176527638715905L;
	private Date startTime,endTime;
	private String region;
	private TreeMap<String, String> timeAndClass;
	private JSONObject rectangleBoxes;
	
	public TreeMap<String, String> getTimeAndClass() {
		return timeAndClass;
	}

	public void setTimeAndClass(TreeMap<String, String> timeAndClass) {
		this.timeAndClass = timeAndClass;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public JSONObject getRectangles() {
		return rectangleBoxes;
	}

	public void setRectangles(JSONObject rectangles) {
		this.rectangleBoxes = rectangles;
	}	
}
