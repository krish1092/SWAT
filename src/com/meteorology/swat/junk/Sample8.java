package com.meteorology.swat.junk;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class Sample8 {

	
	public static void main(String[] args) {
		
		DateTimeZone.setDefault(DateTimeZone.UTC);
		
		
		DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HHmm");
		//dtf.withZoneUTC();
		//Mon Jun 29 18:30:00 CDT 2015
		
		
		DateTime dt = DateTime.parse("06/07/2015 2330", dtf);
		
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		//dtfOut.withZoneUTC();
		DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println(dt);
		
		dt = DateTime.parse(dtfOut.print(dt),dtfOut);
		System.out.println(dt.toDate());
		System.out.println(dtfOut.print(dt));
		
		//System.out.println(dtf.print(dt));
		
	}
}
