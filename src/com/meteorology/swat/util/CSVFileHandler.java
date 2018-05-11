package com.meteorology.swat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.meteorology.swat.DAO.EventDAO;
import com.meteorology.swat.DAOImpl.EventDAOImpl;
import com.meteorology.swat.bean.Event;
import com.meteorology.swat.model.Damages;
import com.meteorology.swat.model.EventsCount;
import com.meteorology.swat.model.LatLongFromUser;





public class CSVFileHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(CSVFileHandler.class);
	
	private Damages damages = new Damages();
	
	private LinkedHashMap<String, EventsCount> eventsCountMap = new LinkedHashMap<String, EventsCount>();
	
	private int total = 0;
	
	public int getTotal(){
		return total;
	}
	
	public LinkedHashMap<String, EventsCount> getEventsCountMap() {
	
		return eventsCountMap;
	}

	public Damages getDamages() {
		return damages;
	}

	public DataSource getDataSourceDetails() throws SQLException{
		SimpleDriverDataSource ds=new SimpleDriverDataSource();
		ds.setDriver(new com.mysql.jdbc.Driver());
		ds.setUrl("jdbc:mysql://localhost:3306/swat");
		ds.setUsername("root");
		//ds.setPassword("Swat@2016");
		ds.setPassword("SwatTool@2015");
		return ds;
	};
	

	
	public void handler(BufferedReader csv,List<NormalizeHelper> cs,String[] keys,Event eventModel,LatLongFromUser latLongs,int index) 
			throws ParseException, NumberFormatException, IOException, SQLException{

		//Time												07/31/2015 1833
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HHmm");

		Event eventReference = eventModel;
		

		//Start and End Dates
		Date startTimeOfThisClass,endTimeOfThisClass;

		double topLatFromUser = latLongs.getNorthLat(),
				bottomLatFromUser = latLongs.getSouthLat(),
				rightLongFromUser = latLongs.getEastLong(),
				leftLongFromUser = latLongs.getWestLong();

		int hailCount=0,thunderstormCount=0,tornadoCount=0,flashfloodCount=0;

		//List for batch insert
		List<Event> eventsList=new ArrayList<Event>();

		//for(int a=0;a < cs.size();a++)
		//{
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		startTimeOfThisClass = dateFormat.parse(keys[cs.get(index).getStartIndex()]);
		endTimeOfThisClass = dateFormat.parse(keys[cs.get(index).getEndIndex()]);
		
		String rows = null,localTimeZone = null;
		double beginLat,beginLong,endLat,endLong,magnitude=0;

		//Read the first line! The first line has the Column names which we do not need.
		csv.readLine();
		
		Normalizer n = new Normalizer();
		boolean allThree = n.allThree(cs) ;
		
		while((rows = csv.readLine())!=null)
		{
			String[] splitrows=rows.split(",");

			//Adding prefix 0s to the time in current row
			localTimeZone = splitrows[13].split("-")[0];

			//Row Date and Time put together for easier comparison
			dateFormat.setTimeZone(TimeZone.getTimeZone(localTimeZone));

			String c = splitrows[3]+ " " + timeRectifier(splitrows[4]);
			Date rowTime = dateFormat.parse(c);

			//The latitude and longitude in each row of the csv
			beginLat=Double.parseDouble(splitrows[30]);
			beginLong=Double.parseDouble(splitrows[31]);
			endLat=Double.parseDouble(splitrows[32]);
			endLong=Double.parseDouble(splitrows[33]);

			//Check if the classified time is within the report's time bound.
			if((rowTime.compareTo(startTimeOfThisClass) >= 0)
					&& (rowTime.compareTo(endTimeOfThisClass) <= 0))
			{

				//Check if the latitudes and longitudes match.
				
				if (((beginLat <= topLatFromUser && beginLat >= bottomLatFromUser && 
					  beginLong <= rightLongFromUser && beginLong >= leftLongFromUser)
						|| 
					(endLat <= topLatFromUser && endLat >= bottomLatFromUser && 
					  endLong <= rightLongFromUser && endLong >= leftLongFromUser)))
				{
					
					calculateDamage(splitrows);
					
					if(splitrows[6]!= null && !splitrows[6].replaceAll("\\s+","").isEmpty())
					{
						magnitude = Double.parseDouble(splitrows[6]);
					}
					
					
					for(String s : splitrows)
						System.out.print(s);
					System.out.println();
					switch(splitrows[5])
					{
					case "Hail":
						if(magnitude > 0.75)
						{
							hailCount++;
							if (allThree) {
								eventModel = new Event();
								eventModel.setInfoId(eventReference.getInfoId());
								eventModel.setClassificationId(eventReference.getClassificationId());
								eventModel.setMagnitude(magnitude);
								eventModel.setEventType("hail");
								eventModel.setState(splitrows[12]);
								eventsList.add(eventModel);
							}
						}
						break;

					case "Thunderstorm Wind":
						if(magnitude > 50)
						{
							thunderstormCount++;
							if (allThree) {
								eventModel = new Event();
								eventModel.setInfoId(eventReference.getInfoId());
								eventModel.setClassificationId(eventReference.getClassificationId());
								eventModel.setMagnitude(magnitude);
								eventModel.setEventType("thunderstorm");
								eventModel.setState(splitrows[12]);
								eventsList.add(eventModel);
							}
						}
						break;

					case "Tornado":
						tornadoCount++;
						String tornadoFScale=splitrows[7];
						if (allThree) {
							eventModel = new Event();
							eventModel.setInfoId(eventReference.getInfoId());
							eventModel.setClassificationId(eventReference.getClassificationId());
							eventModel.setTornadoFScale(tornadoFScale);
							eventModel.setEventType("tornado");
							eventModel.setState(splitrows[12]);
							eventsList.add(eventModel);
						}
						break;

					case "Flash Flood":
						flashfloodCount++;
						if (allThree) {
							eventModel = new Event();
							eventModel.setInfoId(eventReference.getInfoId());
							eventModel.setClassificationId(eventReference.getClassificationId());
							eventModel.setEventType("flashflood");
							eventModel.setState(splitrows[12]);
							eventsList.add(eventModel);
						}
						break;

					default:
						logger.info("Event not of interest");

					}
				}
			}
			else if (rowTime.compareTo(endTimeOfThisClass) > 0){
				break;
			}



		}

		//}

		//Insert into the Events Tables

		for(Event event: eventsList){
			System.out.println(event.getEventType() + event.getMagnitude() + event.getState());
			
		}
	
		if(eventsList.size()>0)
		{
			EventDAO eDAO=new EventDAOImpl();

			eDAO.setDataSource(getDataSourceDetails());
			eDAO.insert(eventsList);
		}
		//csv.close();

		//Return the count of the events
		String currentClass = cs.get(index).getCurrentClassification();
		System.out.println("hailCount:" + hailCount +"tornadoCount:"+ tornadoCount+
				"thunderstormCount:"+ thunderstormCount+"flashfloodCount:"+ flashfloodCount);
		
		
		this.total = this.total + hailCount + tornadoCount + thunderstormCount + flashfloodCount;
		
		addEventCountInMap(currentClass, hailCount, tornadoCount, thunderstormCount, flashfloodCount);
		

	}
	
	public Calendar getCalendarInstance(Date d,String timeZone)
	{
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		c.setTimeZone(TimeZone.getTimeZone(timeZone));
		return c;
	}

	
	private void addEventCountInMap(String classification, int hailCount, int tornadoCount, int thunderstormWindCount,int flashfloodCount){
		
		EventsCount eventsCount = eventsCountMap.get(classification);
		
		if(eventsCount == null)
		{
			eventsCount = new EventsCount();
			eventsCount.setHailCount(hailCount);
			eventsCount.setTornadoCount(tornadoCount);
			eventsCount.setThunderstormWindCount(thunderstormWindCount);
			eventsCount.setFlashfloodCount(flashfloodCount);
		}
		else
		{
			eventsCount.setHailCount( eventsCount.getHailCount() + hailCount );
			eventsCount.setTornadoCount( eventsCount.getTornadoCount() + tornadoCount );
			eventsCount.setThunderstormWindCount( eventsCount.getThunderstormWindCount() + thunderstormWindCount );
			eventsCount.setFlashfloodCount( eventsCount.getFlashfloodCount() + flashfloodCount );
		}
		
		System.out.println("Inside the Map");
		System.out.println("hailCount:" + eventsCount.getHailCount() +"tornadoCount:"+ eventsCount.getTornadoCount()+
				"thunderstormCount:"+ eventsCount.getThunderstormWindCount()+"flashfloodCount:"+ eventsCount.getFlashfloodCount());
		
		eventsCountMap.put(classification, eventsCount);
	}
	
	
	

	public String timeRectifier(String s)
	{
		switch(s.length()){
		case 1:
			s="000"+s;
			break;
			
		case 2:
			s="00"+s;
			break;
		
		case 3:
			s="0"+s;
			break;
		
		}
		return s;
	}

	public void calculateDamage(String[] splitrows)
	{
		damages.setDeathCount(damages.getDeathCount() + Integer.parseInt(splitrows[8]));
		damages.setInjuryCount(damages.getInjuryCount() + Integer.parseInt(splitrows[9]));
		damages.setPropertyDamage(damages.getPropertyDamage() + Long.parseLong(splitrows[10]));
		damages.setCropDamage(damages.getCropDamage() + Long.parseLong(splitrows[11]));
	}
	
	
/*	public String timeConvert(String time, String convertTo) throws ParseException{
		
		//Converting from UTC TO NCDC Local Time Zone
				SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy HHmm");
				
				startAndEndDates.getStartDate();
				
				String dateString = UTCDate + " " + time;
				
				//Radar Images are in UTC timezone
				TimeZone fromUTCTimeZone = TimeZone.getTimeZone("UTC");
				
				Date date = format.parse(dateString);
				SimpleDateFormat ncdcFormat = new SimpleDateFormat("MM/dd/yyyy HHmm");
				TimeZone toNCDCLocalTimeZone = TimeZone.getTimeZone(convertTo);
				
				//Converting the UTC time to NCDC time.
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.setTimeZone(toNCDCLocalTimeZone);
				
				//UTC TO US TIMEZONE - DAYLIGHT SAVINGS OFFSET
				
				Calendar UTCCalendar = Calendar.getInstance();
				UTCCalendar.setTime(date);
				UTCCalendar.setTimeZone(fromUTCTimeZone);
					
				if(toNCDCLocalTimeZone.inDaylightTime(UTCCalendar.getTime()))
					UTCCalendar.add(Calendar.MILLISECOND, toNCDCLocalTimeZone.getRawOffset());
				
				String localTime = ncdcFormat.format(UTCCalendar.getTime()).split("\\s+")[1];
				
				return localTime;
	}
	
	
	//List<Date> listOfDates = getListOfDates(jObj.getString("startTime"), jObj.getString("endTime"));
	
	public List<Date> getListOfDates(String startTimeFromUser, String endTimeFromUser) throws ParseException
	{

		List<Date> listOfDates = new ArrayList<Date>();

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HHmm");

		TimeZone UTCTimeZone = TimeZone.getTimeZone("UTC");
		format.setTimeZone(UTCTimeZone);

		Date startDate = format.parse(startTimeFromUser);
		Date endDate = format.parse(endTimeFromUser);

		Calendar calendarStart = new GregorianCalendar();
		calendarStart.setTime(startDate);
		calendarStart.setTimeZone(UTCTimeZone);

		Calendar calendarEnd = new GregorianCalendar();
		calendarEnd.setTime(endDate);
		calendarEnd.setTimeZone(UTCTimeZone);

		//adding dates to the list as long as start date does not cross the end date
		while (calendarStart.compareTo(calendarEnd) <= 0) {
			listOfDates.add(calendarStart.getTime());
			calendarStart.add(Calendar.DAY_OF_MONTH, 1);

		}

		return listOfDates;
	
	}
	
	
	*/

	
}
