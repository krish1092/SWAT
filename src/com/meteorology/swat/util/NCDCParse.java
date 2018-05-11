package com.meteorology.swat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.meteorology.swat.DAO.EventStorageDAO;
import com.meteorology.swat.model.EventStorageModel;
import com.meteorology.swat.model.StartAndEndDates;

public class NCDCParse {
	@Autowired
	EventStorageDAO events;


	RegionAndLocationUtility regionandlocation=new RegionAndLocationUtility();
	EventStorageModel eventStorageModel=new EventStorageModel(); 


	private final Map<String, Integer> myMap = regionandlocation.createStateIndex();
	private static final Logger logger = LoggerFactory.getLogger(NCDCParse.class);


	public BufferedReader parse(String state,StartAndEndDates startAndEndDates)
	{
		Calendar calendarStart = getCalendarInstance(startAndEndDates.getStartDate());
		Calendar calendarEnd = getCalendarInstance(startAndEndDates.getEndDate());

		String url=getURL(calendarStart, calendarEnd , state);
		logger.info(url);
		try
		{
			URL ncdcURL = new URL(url);
			BufferedReader br = new BufferedReader(new InputStreamReader(ncdcURL.openStream()));
			return br;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	public Calendar getCalendarInstance(Date d)
	{
		Calendar c = new GregorianCalendar();
		c.setTime(d);

		return c;
	}


	public String getURL(Calendar beginDate, Calendar endDate, String state)
	{

		String url="https://www.ncdc.noaa.gov/stormevents/csv?"
				
				//Event types considered
				+ "eventType=%28C%29+Flash+Flood"
				+ "&eventType=%28C%29+Hail"
				+ "&eventType=%28C%29+Thunderstorm+Wind"
				+ "&eventType=%28C%29+Tornado"
				
				//Start Date
				+ "&beginDate_mm="+ zeroPrepend((beginDate.get(Calendar.MONTH)+1)) /* Gregorian Calendar has month starting from 0 (i.e, January =0, Feb =1). hence 1 is added. */
				+ "&beginDate_dd="+ zeroPrepend(beginDate.get(Calendar.DAY_OF_MONTH))
				+ "&beginDate_yyyy="+ beginDate.get(Calendar.YEAR)

				//End Date
				+ "&endDate_mm="+ zeroPrepend((endDate.get(Calendar.MONTH)+1))
				+ "&endDate_dd="+ zeroPrepend(endDate.get(Calendar.DAY_OF_MONTH)) 
				+ "&endDate_yyyy="+ endDate.get(Calendar.YEAR)

				//Other fields
				+ "&county=ALL"

				//The filter values are as mandated by the requirements - the filters as such do not work correctly. Hence the default values are given. 
				+ "&hailfilter=0.00"
				+ "&tornfilter=0"
				+ "&windfilter=000"
				+ "&sort=DT"
				+ "&submitbutton=Search"
				+ "&statefips="+myMap.get(state)+"%2C"+state;

		return url;

	}		
	
	private String zeroPrepend(int i)
	{
		String s = String.valueOf(i);
		if(i < 10) s = "0" + s;
		return s;
	}


	
}
