package com.meteorology.swat.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meteorology.swat.model.RegionsAndTimeZones;

public class RegionAndLocation {

	
	private String [] timeZones={"EST","CST","MST","PST"};
	
	public String[] getMultiTimeZoneStates (String state)
	{
		
		HashMap<String, String[]> statesAndTimeZones = new HashMap<String , String[]>();
		//CST AND EST
		String[] centreEast = {"CST","EST"};
		String[] mountCentre = {"MST","CST"};
		String[] pacificMount = {"PST","MST"};
		
		//CST AND EST
		statesAndTimeZones.put("FLORIDA", centreEast);
		statesAndTimeZones.put("KENTUCKY", centreEast);
		statesAndTimeZones.put("TENNESSEE", centreEast);
		statesAndTimeZones.put("INDIANA", centreEast);
		statesAndTimeZones.put("MICHIGAN", centreEast);
		
		//FLORIDA,KENTUCKY,TENNESSEE,INDIANA,MICHIGAN,
		
		//MST AND CST
		
		statesAndTimeZones.put("TEXAS",mountCentre);
		statesAndTimeZones.put("KANSAS",mountCentre);
		statesAndTimeZones.put("NEBRASKA",mountCentre);
		statesAndTimeZones.put("SOUTH+DAKOTA",mountCentre);
		statesAndTimeZones.put("NORTH+DAKOTA",mountCentre);
		
		//PST AND MST
		statesAndTimeZones.put("IDAHO", pacificMount);
		statesAndTimeZones.put("OREGON", pacificMount);
		
		
		//Single Time Zones
		
		String[] cst= {"CST"};
		String[] est= {"EST"};
		String[] pst= {"PST"};
		String[] mst= {"MST"};
		
		//CST
		statesAndTimeZones.put("", cst);
		
		
		
		
		statesAndTimeZones.put("ALABAMA",cst);
		statesAndTimeZones.put("ARIZONA",mst);
		statesAndTimeZones.put("ARKANSAS",cst);
		statesAndTimeZones.put("CALIFORNIA",pst);		
		statesAndTimeZones.put("COLORADO",mst);
		statesAndTimeZones.put("CONNECTICUT",est);
		statesAndTimeZones.put("DELAWARE",est);
		statesAndTimeZones.put("DISTRICT+OF+COLUMBIA",est);
		statesAndTimeZones.put("GEORGIA",est);
		statesAndTimeZones.put("ILLINOIS",cst);
		statesAndTimeZones.put("IOWA",cst);
		statesAndTimeZones.put("LOUSIANA",cst);
		statesAndTimeZones.put("MAINE",est);
		statesAndTimeZones.put("MARYLAND",est);
		statesAndTimeZones.put("MASSACHUSETTS",est);
		statesAndTimeZones.put("MINNESOTA",cst);
		statesAndTimeZones.put("MISSISSIPPI",cst);
		statesAndTimeZones.put("MISSOURI",cst);
		statesAndTimeZones.put("MONTANA",mst);
		statesAndTimeZones.put("NEVADA",pst);
		statesAndTimeZones.put("NEW+HAMPSHIRE",est);
		statesAndTimeZones.put("NEW+JERSEY",est);
		statesAndTimeZones.put("NEW+MEXICO",mst);
		statesAndTimeZones.put("NEW+YORK",est);
		statesAndTimeZones.put("NORTH+CAROLINA",est);
		statesAndTimeZones.put("OHIO",est);
		statesAndTimeZones.put("OKLAHOMA",cst);
		statesAndTimeZones.put("PENNSYLVANIA",est);
		statesAndTimeZones.put("RHODE+ISLAND",est);
		statesAndTimeZones.put("SOUTH+CAROLINA",est);
		statesAndTimeZones.put("UTAH",mst);
		statesAndTimeZones.put("VERMONT",mst);
		statesAndTimeZones.put("VIRGINIA",est);
		statesAndTimeZones.put("WASHINGTON",pst);
		statesAndTimeZones.put("WEST+VIRGINIA",est);
		statesAndTimeZones.put("WISCONSIN",cst);
		statesAndTimeZones.put("WYOMING",mst);
		
		return statesAndTimeZones.get(state);
		
		
	}
	
	
	
	
	/*public Map<String, String[]> createRegionState() {
		HashMap<String, String[]> result = new HashMap<String, String[]>();
		
		
		
		
		
		String[] centPlains={"IOWA","NEBRASKA","MISSOURI","KANSAS","OKLAHOMA","ARKANSAS"};
		String[] centMissVly={"IOWA","MISSOURI","ILLINOIS","INDIANA","PENNSYLVANIA","KENTUCKY","WEST+VIRGINIA","TENNESSEE"};
		String[] midAtlantic={"MARYLAND","NEW+JERSEY","OHIO","NORTH+CAROLINA","DELAWARE","VIRGINIA","WEST+VIRGINIA",
				"RHODE+ISLAND","CONNECTICUT","NEW+YORK"};
		String[] newEngland={"MAINE","NEW+HAMPSHIRE","VERMONT","NEW+YORK","DELAWARE","NEW+JERSEY",
				"PENNSYLVANIA","MASSACHUSETTS","RHODE+ISLAND","CONNECTICUT"};
		String[] northPlains={"NORTH+DAKOTA","SOUTH+DAKOTA","NEBRASKA","MINNESOTA","IOWA"};
		String[] southMissvly={"ARKANSAS","MISSISSIPPI","ALABAMA","GEORGIA","LOUISIANA","TENNESSEE"};
		String[] southPlains={"OKLAHOMA","TEXAS","NEW+MEXICO"};
		String[] southeast={"ALABAMA","GEORGIA","SOUTH+CAROLINA","NORTH+CAROLINA","VIRGINIA"};
		String[] upperMissvly={"IOWA","MISSOURI","ILLINOIS","WISCONSIN","INDIANA","KENTUCKY","MICHIGAN"};
		String[] national={"FLORIDA","ARIZONA","NEW+MEXICO","CALIFORNIA","OREGON","WASHINGTON","IDAHO","NEVADA","UTAH"};
		result.put("cent_plains",centPlains);
		result.put("cent_missvly", centMissVly);
		result.put("mid_atlantic", midAtlantic);
		result.put("new_england", newEngland);
		result.put("north_plains", northPlains);
		result.put("south_missvly", southMissvly);
		result.put("south_plains", southPlains);
		result.put("southeast", southeast);
		result.put("upper_missvly", upperMissvly);
		result.put("national", national);
		return Collections.unmodifiableMap(result);
	}*/

	public String[] getTimeZones() {
		return timeZones;
	}

	
	public Map<String, Integer> createStateIndex() {
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("ALABAMA",1);
		result.put("ALASKA",2);		
		result.put("ARIZONA",4);
		result.put("ARKANSAS",5);
		result.put("CALIFORNIA",6);		
		result.put("COLORADO",8);
		result.put("CONNECTICUT",9);
		result.put("DELAWARE",10);
		result.put("DISTRICT+OF+COLUMBIA",11);
		result.put("FLORIDA",12);
		result.put("GEORGIA",13);
		result.put("HAWAII",14);		
		result.put("IDAHO",16);
		result.put("ILLINOIS",17);
		result.put("INDIANA",18);
		result.put("IOWA",19);
		result.put("KANSAS",20);
		result.put("KENTUCKY",21);
		result.put("LOUSIANA",22);
		result.put("MAINE",23);
		result.put("MARYLAND",24);
		result.put("MASSACHUSETTS",25);
		result.put("MICHIGAN",26);
		result.put("MINNESOTA",27);
		result.put("MISSISSIPPI",28);
		result.put("MISSOURI",29);
		result.put("MONTANA",30);
		result.put("NEBRASKA",31);
		result.put("NEVADA",32);
		result.put("NEW+HAMPSHIRE",33);
		result.put("NEW+JERSEY",34);
		result.put("NEW+MEXICO",35);
		result.put("NEW+YORK",36);
		result.put("NORTH+CAROLINA",37);
		result.put("NORTH+DAKOTA",38);
		result.put("OHIO",39);
		result.put("OKLAHOMA",40);
		result.put("OREGON",41);
		result.put("PENNSYLVANIA",42);
		result.put("RHODE+ISLAND",43);
		result.put("SOUTH+CAROLINA",44);
		result.put("SOUTH+DAKOTA",46);
		result.put("TENNESSEE",47);
		result.put("TEXAS",48);
		result.put("UTAH",49);
		result.put("VERMONT",50);
		result.put("VIRGINIA",51);
		result.put("WASHINGTON",53);
		result.put("WEST+VIRGINIA",54);
		result.put("WISCONSIN",55);
		result.put("WYOMING",56);
		return Collections.unmodifiableMap(result);
	}

	public Map<String,Double[]> LatitudeLongitudeMap(){

		Map<String, Double[]> result = new HashMap<String, Double[]>();

		double longLeft,longRight,latTop,latBottom;

		double longDiff,latDiff;

		Double[] putInResult;

		//newEngland lats and longs
		longLeft=-81.850417;
		longRight=-64.909500;
		longDiff=longRight-longLeft;

		latTop=47.548000;
		latBottom=37.736305;
		latDiff=latTop-latBottom;

		putInResult=new Double[]{longLeft,longRight,latTop,latBottom};

		result.put("new_england", putInResult);















		//centPlains lats and longs
		longLeft=-107.7719335;
		longRight=-90.182822;
		longDiff=longRight-longLeft;

		latTop=43.4398685;
		latBottom=33.039404;
		latDiff=latTop-latBottom;
		putInResult=new Double[]{longLeft,longRight,latTop,latBottom};
		result.put("cent_plains",putInResult);

		//centMissVly lats and longs

		longLeft=-96.093312;
		longRight=-78.011325;
		longDiff=longRight-longLeft;

		latTop=43.2554045;
		latBottom=33.119975;
		latDiff=latTop-latBottom;
		putInResult=new Double[]{longLeft,longRight,latTop,latBottom};
		result.put("cent_missvly", putInResult);

		//midAtlantic lats and longs
		longLeft=-68.8919415;
		longRight=-88.2281705;
		longDiff=longRight-longLeft;

		latTop=43.300653;
		latBottom=33.359741;
		latDiff=latTop-latBottom;
		putInResult=new Double[]{longLeft,longRight,latTop,latBottom};
		result.put("mid_atlantic", putInResult);

		//northPlains lats and longs
		longLeft=-109.184462;
		longRight=-90.658264;
		longDiff=longRight-longLeft;

		latTop=48.784145;
		latBottom=39.1699115;
		latDiff=latTop-latBottom;
		putInResult=new Double[]{longLeft,longRight,latTop,latBottom};
		result.put("north_plains", putInResult);

		//southMissvly lats and longs
		longLeft=-97.711068;
		longRight=-80.627328;
		longDiff=longRight-longLeft;

		latTop=38.094011;
		latBottom=27.842124;
		latDiff=latTop-latBottom;
		putInResult=new Double[]{longLeft,longRight,latTop,latBottom};
		result.put("south_missvly", putInResult);


		//southPlains lats and longs
		longLeft=-106.9615565;
		longRight=-89.8778165;
		longDiff=longRight-longLeft;

		latTop=38.1376025;
		latBottom=27.5312685;
		latDiff=latTop-latBottom;
		putInResult=new Double[]{longLeft,longRight,latTop,latBottom};
		result.put("south_plains", putInResult);

		//southeast lats and longs
		longLeft=-91.514779;
		longRight=-74.6507655;
		longDiff=longRight-longLeft;

		latTop=37.5018175;
		latBottom=26.8078285;
		latDiff=latTop-latBottom;
		putInResult=new Double[]{longLeft,longRight,latTop,latBottom};
		result.put("southeast", putInResult);

		//upperMissvly  lats and longs
		longLeft=-94.733773;
		longRight=-76.2756875;
		longDiff=longRight-longLeft;

		latTop=48.6744925;
		latBottom=38.649896;
		latDiff=latTop-latBottom;
		putInResult=new Double[]{longLeft,longRight,latTop,latBottom};
		result.put("upper_missvly", putInResult);

		//national lats and longs
		longLeft=-130.050503;
		longRight=-68.1204805;
		longDiff=longRight-longLeft;

		latTop=50.7149875;
		latBottom=21.304657;
		latDiff=latTop-latBottom;
		putInResult=new Double[]{longLeft,longRight,latTop,latBottom};
		result.put("national", putInResult);
		/*Dr.Gallus Method
		 * 
		 * width=800;
	var height=600;
		 * 
		var lat=latTop- ((x/height)*latDiff);
		var long=longLeft+((y/width)*longDiff);
		console.log(lat+","+long);
		alert(lat+","+long);*/


		return Collections.unmodifiableMap(result);
	}


	
	public List<RegionsAndTimeZones> regionTimezones () {
		
		List<RegionsAndTimeZones> regionsTimeZoneList = new ArrayList<RegionsAndTimeZones>();
		
		String[] timeZones={"EST","CST","MST","PST"};
		
		RegionsAndTimeZones regionsAndTimeZones = new RegionsAndTimeZones();
		
		regionsAndTimeZones.setRegion("mid_atlantic");
		
		
		regionsAndTimeZones.setTimeZones(timeZones);
		regionsTimeZoneList.add(regionsAndTimeZones);
		
		//southeast
		regionsAndTimeZones = new RegionsAndTimeZones();
		
		regionsAndTimeZones.setRegion("southeast");
		
		
		regionsAndTimeZones.setTimeZones(timeZones);
		regionsTimeZoneList.add(regionsAndTimeZones);
		
		
		
		regionsAndTimeZones = new RegionsAndTimeZones();
		
		regionsAndTimeZones.setRegion("north_plains");
		
		
		regionsAndTimeZones.setTimeZones(timeZones);
		regionsTimeZoneList.add(regionsAndTimeZones);
		
		
		regionsAndTimeZones = new RegionsAndTimeZones();
		
		regionsAndTimeZones.setRegion("cent_plains");
		
		
		regionsAndTimeZones.setTimeZones(timeZones);
		regionsTimeZoneList.add(regionsAndTimeZones);
		
		
		
		regionsAndTimeZones = new RegionsAndTimeZones();
		
		regionsAndTimeZones.setRegion("new_england");
		
		
		regionsAndTimeZones.setTimeZones(timeZones);
		regionsTimeZoneList.add(regionsAndTimeZones);
		
		
		
		regionsAndTimeZones = new RegionsAndTimeZones();
		
		regionsAndTimeZones.setRegion("south_plains");
		
		
		regionsAndTimeZones.setTimeZones(timeZones);
		regionsTimeZoneList.add(regionsAndTimeZones);
		
		
		
		
		
		regionsAndTimeZones = new RegionsAndTimeZones();
		
		regionsAndTimeZones.setRegion("south_missvly");
		
		
		regionsAndTimeZones.setTimeZones(timeZones);
		regionsTimeZoneList.add(regionsAndTimeZones);
		
			
		regionsAndTimeZones = new RegionsAndTimeZones();
		
		regionsAndTimeZones.setRegion("upper_missvly");
		
		
		regionsAndTimeZones.setTimeZones(timeZones);
		regionsTimeZoneList.add(regionsAndTimeZones);
		
		
		regionsAndTimeZones = new RegionsAndTimeZones();
		
		regionsAndTimeZones.setRegion("cent_missvly");
		
		
		regionsAndTimeZones.setTimeZones(timeZones);
		regionsTimeZoneList.add(regionsAndTimeZones);
		
		
		return regionsTimeZoneList;
		
		
		
	}
	
}
