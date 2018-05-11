package com.meteorology.swat.bean;

import java.math.BigInteger;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class UnmodifiedClassification {
	
	private BigInteger infoID;
	private String classification;
	private String dateTime;
	
	public BigInteger getInfoID() {
		return infoID;
	}
	public void setInfoID(BigInteger infoID) {
		this.infoID = infoID;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {

		DateTimeZone.setDefault(DateTimeZone.UTC);
		DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HHmm");
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime dt = DateTime.parse(dateTime , dtf);
		this.dateTime = dtfOut.print(dt);
	}
	

}
