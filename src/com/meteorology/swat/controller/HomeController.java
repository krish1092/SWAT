package com.meteorology.swat.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.meteorology.swat.DAO.ClassificationDAO;
import com.meteorology.swat.DAO.EventStorageDAO;
import com.meteorology.swat.DAO.InformationDAO;
import com.meteorology.swat.DAOImpl.ClassificationDAOImpl;
import com.meteorology.swat.DAOImpl.EventStorageDAOImpl;
import com.meteorology.swat.DAOImpl.InformationDAOImpl;
import com.meteorology.swat.bean.Classification;
import com.meteorology.swat.bean.Event;
import com.meteorology.swat.bean.Information;
import com.meteorology.swat.bean.LatLongFromDB;
import com.meteorology.swat.bean.Rectangle;
import com.meteorology.swat.model.EventUser;
import com.meteorology.swat.model.FormClass;
import com.meteorology.swat.model.LatLongFromUser;
import com.meteorology.swat.model.StartAndEndDates;
import com.meteorology.swat.service.FrontPageService;
import com.meteorology.swat.service.LatLongService;
import com.meteorology.swat.service.UnmodifiedClassificationService;
import com.meteorology.swat.util.CSVFileHandler;
import com.meteorology.swat.util.DBProperties;
import com.meteorology.swat.util.NCDCParse;
import com.meteorology.swat.util.NormalizeHelper;
import com.meteorology.swat.util.Normalizer;
import com.meteorology.swat.util.Utilities;



/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes("sessionId")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private String sessionId;
	EventUser eventsUserDAO=new EventUser();
	HashMap<String,Integer> mostClassified=new HashMap<String,Integer>();
	
	@Autowired
	EventStorageDAO events;//=new EventStorage();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String frontPage(HttpSession session,Locale locale, Model model) {
		model.addAttribute("frontPageDetails",new FrontPageService().getFrontPageDetails());
		return "front-page";
	}
	
	@RequestMapping(value = "/select-region", method = RequestMethod.GET)
	public String selectRegion(HttpSession session,Locale locale, Model model) {
		this.sessionId = session.getId();
		logger.info("session ID:"+sessionId);
		return "select-region";
	}
	

	@RequestMapping(value = "/showimage", method = RequestMethod.GET)
	public String showImage(@RequestParam String region,HttpSession session, Model model) throws SQLException {

		this.sessionId = session.getId();	
		logger.info("Region:"+region +","+"sessionId :"+sessionId);
		return "showimage";
	}
	
	
	@RequestMapping(value = "/storedetails", method = RequestMethod.POST)
	public String storeDetails(@ModelAttribute("formClass")  FormClass formClass,HttpSession session, Model model) throws SQLException, 
			 ParseException {

		this.sessionId = session.getId();
		logger.info(formClass.getHiddenJSON());

		String json = formClass.getHiddenJSON();
		JSONObject jObj = new JSONObject(json);
		
		logger.info("sessionId in NCDC:::" + sessionId);

		// Check how long the program runs
		long startTime = System.currentTimeMillis();

		String region = jObj.getString("region");

		// Get the states in the selected region
		String[] states = getStates(region);

		String centreImgTime = jObj.getString("centreTime");
		String startTimeFromUser = jObj.getString("startTime");
		String endTimeFromUser = jObj.getString("endTime");
		
		//Start And End Dates
		
		Utilities utility = new Utilities();
		StartAndEndDates startAndEndDates = new StartAndEndDates();
		startAndEndDates.setStartDate(utility.date(startTimeFromUser));
		startAndEndDates.setEndDate(utility.date(endTimeFromUser));
		
		//Reference latitude and longitude for calculating the rubber band box's latitude longitude.
		LatLongService latLongService = new LatLongService();
		
		//Rubber band box coordinates
		Rectangle rectangle = new Rectangle(jObj.getJSONObject("rectangle"));
		
		List<LatLongFromDB> referenceLatLongList = latLongService.getReferenceLatLong(startAndEndDates, region);
		LatLongFromUser latLongFromUser = new LatLongFromUser();
		
		if(referenceLatLongList.size() == 1){
			
			// Both the start and end dates from the user fit within a reference window
			LatLongFromDB reference = referenceLatLongList.get(0);
			 latLongFromUser = getLatitudeLongitude(reference, rectangle);
			
		}else if(referenceLatLongList.size() > 1){
			// Multiple latlongs. The date from user falls in a window where the reference latlongs change.
			
			List<LatLongFromUser> listOfLatLongFromUser = new ArrayList<LatLongFromUser> ();
			for(LatLongFromDB reference : referenceLatLongList)
			{
				 latLongFromUser = getLatitudeLongitude(reference, rectangle);
				listOfLatLongFromUser.add(latLongFromUser);
			}
			
			
		}else{
			//No reference latlong found.
		}
		
		
		//Storing Into Information Table
		Information information = new Information();
		information.setSessionID(sessionId);
		
		
		//Joda - DateTime for start and end Times
		DateTimeZone.setDefault(DateTimeZone.UTC);
		DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HHmm");
		DateTimeFormatter dtfOutput = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		
		DateTime tempDate = DateTime.parse(startTimeFromUser , dtf);
		
		//Start Time
		information.setStartDateTime(dtfOutput.print(tempDate));
		
		//End Time
		tempDate = DateTime.parse(endTimeFromUser , dtf);
		information.setEndDateTime(dtfOutput.print(tempDate));
		
		//Centre Image Time
		tempDate = DateTime.parse(centreImgTime , dtf);
		information.setCentreImageTime(dtfOutput.print(tempDate));
		
		//Region
		information.setRegion(region);
		
		//Latitudes and Longitudes
		information.setRectEastLong(latLongFromUser.getEastLong());
		information.setRectWestLong(latLongFromUser.getWestLong());
		information.setRectNorthLat(latLongFromUser.getNorthLat());
		information.setRectSouthLat(latLongFromUser.getSouthLat());
		
		if(session.getAttribute("userEmail") != null)
			information.setEmailAddress((String)session.getAttribute("userEmail"));
		
		
		SingleConnectionDataSource singleConnectionDataSource = getSingleConnectionDataSource();
		//InformationService infoServiceClass = new InformationService();
		
		//Info ID of this classification - Common link across multiple tables
		BigInteger infoID = storeInInformationTable(information, singleConnectionDataSource);

		logger.info(infoID.toString());
		//Storing into information table ends.

		// NCDC Parse - To get the csv files corresponding to the date from
		// startDate to EndDate across states and timeZones
		NCDCParse ncdc = new NCDCParse();

		
		TreeMap< String, String> timeAndClassTreeMap = getTreeMap(jObj.getJSONObject("timeAndClass"));
		
		
		UnmodifiedClassificationService unmodifiedClassificationService = new UnmodifiedClassificationService(timeAndClassTreeMap, infoID);
		unmodifiedClassificationService.insertIntoUnmodifiedClassification();
		
		// Normalizer Starts here.
		Normalizer normalizer = new Normalizer();
		
		//Normalized List
		List<NormalizeHelper> normalizeHelperList = normalizer.makeList(timeAndClassTreeMap);
		
		String[] keys = normalizer.getKeys();

		boolean noNaturalTriplet = normalizer.isNoNaturalTriplet(normalizeHelperList);

		boolean modifiedTriplet = false;

		if (!normalizer.allThree(normalizeHelperList)) 
		{
			normalizeHelperList = normalizer.normalize(normalizeHelperList);
			modifiedTriplet = normalizer.isModifiedTriplet();
		}

		double sum = (double) normalizer.getSumOfTripletCount(normalizeHelperList);
		double total = (double) normalizer.getTotalCount(normalizeHelperList);

		if (!normalizer.allThree(normalizeHelperList)
				&& (!noNaturalTriplet || (modifiedTriplet && sum / total > 0.5))) 
		{
			normalizeHelperList = normalizer.gallusNormalization(normalizeHelperList);
		}

		
		// Storing in classification Table
		List<Event> eventModelList = storeInClassificationTable(infoID, singleConnectionDataSource, normalizeHelperList, keys);

		// Time Zone Conversion

		System.out.println(eventModelList.size());
		
		/*RegionAndLocationUtility reg = new RegionAndLocationUtility();
		String[] timeZonesInRegion = reg.getTimeZones();*/

		/*HashMap<String, StartAndEndDates> datesMap = new HashMap<String, StartAndEndDates>();

		
		datesMap = utility.timeZoneConversion(startTimeFromUser, endTimeFromUser, timeZonesInRegion);
*/
		
		startAndEndDates = utility.easyConvert(startAndEndDates, utility.getTimeZones(region));
		// Time Zone Conversion Ends
		
		//CSV File Handling
		CSVFileHandler csvFileHandler = new CSVFileHandler();
		//boolean alreadyMatched = false;
		
		//To be implemented - Non repetitive calls
		
			for (int i = 0; i < states.length; i++) {
				/*String[] timeZones = reg.getMultiTimeZoneStates(states[i]);*/
				
				/*for (int j = 0; j < timeZones.length; j++) {*/

					//Calendar timeZoneDate = new GregorianCalendar(TimeZone.getTimeZone(timeZones[j]));
					
					//	alreadyMatched = true;
						
						try
						{
							BufferedReader br = ncdc.parse(states[i], startAndEndDates);
							if(br != null)
							{
								for (int index = 0 ; index < eventModelList.size(); index++) 
								{
									eventModelList.get(index).setState(states[i]);
									csvFileHandler.handler(br, normalizeHelperList, keys, eventModelList.get(index), latLongFromUser, index);
								}
							}
							else
							{
								model.addAttribute("error", "Error fetching the Storm reports for this date");
								return "error";
							}
							
						}catch(IOException e)
						{
							e.printStackTrace();
							model.addAttribute("error", "Something went wrong when processing the Storm reports");
							return "error";
						}
						
						
				/*}*/
			}

			
		long endTime = System.currentTimeMillis();
		logger.info("That took " + (endTime - startTime) + " milliseconds");

		model.addAttribute("timeAndClassTreeMap",timeAndClassTreeMap);
		model.addAttribute("damages", csvFileHandler.getDamages());
		model.addAttribute("classwiseEventsCount", csvFileHandler.getEventsCountMap());
		model.addAttribute("region", region);
		model.addAttribute("normalizedMap", normalizer.getNormalizedDateAndClassMap(normalizeHelperList));
		
		if(!normalizer.allThree(normalizeHelperList))
			model.addAttribute("result", "Your System did not sustain on a single morphology consistently");
		else
			model.addAttribute("result", "Your classification has been stored into our database!");
		
		return "storedetails";
	}
	
	
	public TreeMap<String, String> getTreeMap(JSONObject timeAndClass)
	{
		TreeMap<String, String> timeAndClassTreeMap = new TreeMap<String, String>();
		
		Iterator<?> iteratorKeys = timeAndClass.keys();
		while(iteratorKeys.hasNext())
		{
			String key = (String) iteratorKeys.next();
			String value = timeAndClass.getString(key);
			timeAndClassTreeMap.put(key, value);
		}
		
		return timeAndClassTreeMap;
	}
	
	
	public LatLongFromUser getLatitudeLongitude(LatLongFromDB reference, Rectangle rectangle)
	{

		//latitudes 
		//Standard Image Height of the Radar Images
		int imageHeight = 600;
		
		//Calculating the latitude for the region on the specific date.
		double northLatOfRegion = reference.getNorthLatitude();
		double southLatOfRegion = reference.getSouthLatitude();
		
		double latDiff = Math.abs(northLatOfRegion - southLatOfRegion);

		double northLatitudeOfUser = northLatOfRegion - (((double) rectangle.getTop() / imageHeight) * latDiff) + 1 /* Widening user's box*/;
		double southLatitudeOfUser = northLatOfRegion - (( (double) rectangle.getBottom() / imageHeight) * latDiff)- 1 /* Widening user's box*/;
		
		//Longitudes
		
		//Standard Image Width of the Radar Images
		int imageWidth = 800;
				
		//Calculating the longitude for the region on the specific date.
		double westLongOfRegion = reference.getWestLongitude();
		double eastLongOfRegion = reference.getEastLongitude();
		
		double longDiff = Math.abs(eastLongOfRegion - westLongOfRegion);

		double westLongitudeOfUser = westLongOfRegion + (( (double) rectangle.getLeft() / imageWidth ) * longDiff) - 1 /* Widening user's box*/;
		double eastLongitudeOfUser = westLongOfRegion + (( (double) rectangle.getRight() / imageWidth ) * longDiff) + 1 /* Widening user's box*/;
				
		
		LatLongFromUser latLongFromUser= new LatLongFromUser(); 
		latLongFromUser.setNorthLat(northLatitudeOfUser);
		latLongFromUser.setSouthLat(southLatitudeOfUser);
		latLongFromUser.setEastLong(eastLongitudeOfUser);
		latLongFromUser.setWestLong(westLongitudeOfUser);
		
		
		System.out.println("northLatitudeOfUser:" + northLatitudeOfUser + " southLatitudeOfUser:"+ southLatitudeOfUser);
		System.out.println("eastLongitudeOfUser:" + eastLongitudeOfUser + " westLongitudeOfUser:" + westLongitudeOfUser);
		
		return latLongFromUser;

	}
	
	/**
	 * @return The about page.
	 */
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about()
	{
		return "about";
	}
	
	/**
	 * To be modified.
	 * @return
	 * @throws SQLException
	 */
	private DataSource dataSourceDetails() throws SQLException
	{
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new com.mysql.jdbc.Driver());
        dataSource.setUrl(DBProperties.getDbUrl());
        dataSource.setUsername(DBProperties.getDbUsername());
        dataSource.setPassword(DBProperties.getDbPassword());
        return dataSource;
	}
	
	/**
	 * To be modified.
	 * @return
	 */
	private SingleConnectionDataSource getSingleConnectionDataSource()
	{
		SingleConnectionDataSource singleConnectionDataSource = new SingleConnectionDataSource();
		singleConnectionDataSource.setUrl(DBProperties.getDbUrl());
		singleConnectionDataSource.setUsername(DBProperties.getDbUsername());
		singleConnectionDataSource.setPassword(DBProperties.getDbPassword());
		return singleConnectionDataSource;
	}
	
	public BigInteger storeInInformationTable(Information information,SingleConnectionDataSource singleConnectionDataSource)
	{
		InformationDAO informationDAO = new InformationDAOImpl();
		informationDAO.setDataSource(singleConnectionDataSource);
		BigInteger infoID = informationDAO.insert(information);
		return infoID;
	}

	
	public List<Event> storeInClassificationTable(BigInteger infoID, SingleConnectionDataSource singleConnectionDataSource,
			List<NormalizeHelper> normalizeHelperList, String[] keys)
	{
		Classification classificationClass = new Classification();
		classificationClass.setInfoID(infoID);

		ClassificationDAO classificationDAO = new ClassificationDAOImpl();
		classificationDAO.setDataSource(singleConnectionDataSource);

		
		BigInteger classID;

		List<Event> eventModelList = new ArrayList<Event>();

		for(NormalizeHelper normalizeHelper : normalizeHelperList)
		{
			/*classificationClass.setStartTime(keys[normalizeHelper.getStartIndex()]);
			classificationClass.setEndTime(keys[normalizeHelper.getEndIndex()]);*/
			classificationClass.setClassification(normalizeHelper.getCurrentClassification());
			classificationClass.setCount(normalizeHelper.getCurrentClassificationCount());

			classID = classificationDAO.insert(classificationClass);

			//Setting the infoID and classID from the tables, for foreign key mapping
			Event eventModel = new Event();
			eventModel.setInfoId(infoID);
			eventModel.setClassificationId(classID);
			eventModelList.add(eventModel);
		}

		return eventModelList;
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
		String[] states=ed.selectFromRegions(region).getStates();
		return states;
	}
	
}
