package com.meteorology.swat.util;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.meteorology.swat.DAO.EventStorageDAO;
import com.meteorology.swat.DAOImpl.EventStorageDAOImpl;
import com.meteorology.swat.bean.TimeZones;
import com.meteorology.swat.model.StartAndEndDates;



public class Utilities {

	public HashMap<String, StartAndEndDates> timeZoneConversion(String startTime, String endTime, String[] convertTo) throws ParseException
	{

		HashMap<String, StartAndEndDates> dates = new HashMap<String, StartAndEndDates>();

		// Setting the date format
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HHmm");
		String startDateString = startTime;
		String endDateString = endTime;

		// Radar Images are in UTC timezone
		TimeZone tz = TimeZone.getTimeZone("UTC");
		format.setTimeZone(tz);

		Date startDate = format.parse(startDateString);
		Date endDate = format.parse(endDateString);

		for (int i = 0; i < convertTo.length; i++) {

			// Convert to the timeZone of the NCDC Data
			// NCDC data has local time zone for each location. It is converted
			// to UTC here.

			SimpleDateFormat ncdcFormat = new SimpleDateFormat("MM/dd/yyyy HHmm");
			TimeZone ncdcZone = TimeZone.getTimeZone(convertTo[i]);
			ncdcFormat.setTimeZone(ncdcZone);

			// Converting the radar time to NCDC time.
			// StartDate
			Calendar calendarStart = new GregorianCalendar();
			calendarStart.setTime(startDate);
			calendarStart.setTimeZone(ncdcZone);

			// EndDate
			Calendar calendarEnd = new GregorianCalendar();
			calendarEnd.setTime(endDate);
			calendarEnd.setTimeZone(ncdcZone);

			StartAndEndDates startAndEndDates = new StartAndEndDates();
			startAndEndDates.setStartDate(calendarStart.getTime());
			startAndEndDates.setEndDate(calendarEnd.getTime());

			dates.put(ncdcFormat.getTimeZone().getID(), startAndEndDates);
		}

		return dates;

	}
	
	
	
	public HashMap<String,StartAndEndDates> statesAndDates(String region, StartAndEndDates overallUTC)
	{
		HashMap<String,StartAndEndDates> dates = new HashMap<String, StartAndEndDates>();
		
		String[] states = getStates(region);
		
		RegionAndLocationUtility regionAndLocationUtility = new RegionAndLocationUtility();
		StartAndEndDates startAndEndDates = new StartAndEndDates();
		
		for(String state : states)
		{
			String[] timeZones = regionAndLocationUtility.getMultiTimeZoneStates(state);
			startAndEndDates.setStartDate(overallUTC.getStartDate());
			startAndEndDates.setEndDate(overallUTC.getEndDate());
			
			for(String timezone: timeZones)
			{
				StartAndEndDates converted = convertDate(overallUTC, timezone);
				if(startAndEndDates.getStartDate().before(converted.getStartDate()))
				{
					startAndEndDates.setStartDate(converted.getStartDate());
				}
				if(converted.getStartDate().after(startAndEndDates.getEndDate()))
				{
					startAndEndDates.setEndDate(converted.getStartDate());
				}
			}
			
			dates.put(state, startAndEndDates);
		}
		
		return dates;
	}
	
	
	public StartAndEndDates convertDate(StartAndEndDates overallUTC, String convertTo)
	{
		StartAndEndDates startAndEndDates = new StartAndEndDates();
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		// The Overall UTC time
		TimeZone tz = TimeZone.getTimeZone("UTC");
		format.setTimeZone(tz);

		Date startDate = overallUTC.getStartDate();
		Date endDate = overallUTC.getEndDate();
		
		SimpleDateFormat ncdcFormat = new SimpleDateFormat("MM/dd/yyyy");
		TimeZone ncdcZone = TimeZone.getTimeZone(convertTo);
		ncdcFormat.setTimeZone(ncdcZone);

		// Converting the radar time to NCDC time.
		// StartDate
		Calendar calendarStart = new GregorianCalendar();
		calendarStart.setTime(startDate);
		calendarStart.setTimeZone(ncdcZone);

		// EndDate
		Calendar calendarEnd = new GregorianCalendar();
		calendarEnd.setTime(endDate);
		calendarEnd.setTimeZone(ncdcZone);

		startAndEndDates.setStartDate(calendarStart.getTime());
		startAndEndDates.setEndDate(calendarEnd.getTime());
		
		return startAndEndDates;
	}
	
	public StartAndEndDates easyConvert(StartAndEndDates overallUTC, TimeZones tzs)
	{
		StartAndEndDates startAndEndDates = new StartAndEndDates();
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
		// The Overall UTC time
		TimeZone tz = TimeZone.getTimeZone("UTC");
		format.setTimeZone(tz);

		Date startDate = overallUTC.getStartDate();
		Date endDate = overallUTC.getEndDate();
		
		SimpleDateFormat ncdcFormat = new SimpleDateFormat("MM/dd/yyyy");
		TimeZone ncdcZone = TimeZone.getTimeZone(tzs.getWestern());
		ncdcFormat.setTimeZone(ncdcZone);

		// Converting the radar time to NCDC time.
		// StartDate
		Calendar calendarStart = new GregorianCalendar();
		calendarStart.setTime(startDate);
		calendarStart.setTimeZone(ncdcZone);

		// EndDate
		
		ncdcZone = TimeZone.getTimeZone(tzs.getEastern());
		ncdcFormat.setTimeZone(ncdcZone);
		Calendar calendarEnd = new GregorianCalendar();
		calendarEnd.setTime(endDate);
		calendarEnd.setTimeZone(ncdcZone);

		startAndEndDates.setStartDate(calendarStart.getTime());
		startAndEndDates.setEndDate(calendarEnd.getTime());
		
		return startAndEndDates;
	}
	
	
	
	public String[] getStates(String region)
	{
		EventStorageDAO ed=new EventStorageDAOImpl();
		try {
			ed.setDataSource(dataSourceDetails());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		String[] states = ed.selectFromRegions(region).getStates();
		return states;
	}
	
	
	public DataSource dataSourceDetails() throws SQLException
	{
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new com.mysql.jdbc.Driver());
        dataSource.setUrl("jdbc:mysql://localhost:3306/swat");
        dataSource.setUsername("root");
        //dataSource.setPassword("Swat@2016");
        dataSource.setPassword("SwatTool@2015");
        return dataSource;
	}
	
	public Date date(String s) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HHmm");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date d = sdf.parse(s);
		return d;
	}
	
	public TimeZones getTimeZones(String region)
	{
		HashMap<String, TimeZones> h = new HashMap<String, TimeZones>();
		TimeZones tzs = new TimeZones();
		tzs.setEastern("CST");
		tzs.setWestern("CST");
		h.put("cent_plains", tzs);
		
		tzs.setEastern("CST");
		tzs.setWestern("MST");
		h.put("north_plains", tzs);
		
		tzs.setEastern("CST");
		tzs.setWestern("MST");
		h.put("south_plains", tzs);
		
		tzs.setEastern("EST");
		tzs.setWestern("CST");
		h.put("southeast", tzs);
		
		tzs.setEastern("EST");
		tzs.setWestern("EST");
		h.put("new_england", tzs);
		
		tzs.setEastern("EST");
		tzs.setWestern("CST");
		h.put("south_missvly", tzs);
		
		tzs.setEastern("EST");
		tzs.setWestern("CST");
		h.put("cent_missvly", tzs);
		
		tzs.setEastern("EST");
		tzs.setWestern("CST");
		h.put("upper_missvly", tzs);
		
		tzs.setEastern("EST");
		tzs.setWestern("EST");
		h.put("mid_atlantic", tzs);
		
		return h.get(region);
	}
	
}
