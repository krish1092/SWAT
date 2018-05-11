package com.meteorology.swat.junk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Sample3 {

  public static void main(String[] a) throws ParseException{
		
	  String startTimeFromUser ="06/17/2015 0030";
	  String endTimeFromUser = "06/17/2015 0030";
	  
	  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HHmm");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	  
	  Calendar overallStartTimeInUTC = new GregorianCalendar(TimeZone.getTimeZone("CST"));
		overallStartTimeInUTC.setTime(sdf.parse(startTimeFromUser));
		
		//Overall EndTime from User in UTC
		Calendar overallEndTimeInUTC = new GregorianCalendar(TimeZone.getTimeZone("EST"));
		overallEndTimeInUTC.setTime(sdf.parse(endTimeFromUser));
		
		System.out.println(overallEndTimeInUTC.compareTo(overallStartTimeInUTC));
	  
	}
	
}
