package com.meteorology.swat.model;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class ProfileSpecificObservationEventsCount {
	
	private String hailCount, thunderstormCount, tornadoCount, flashfloodCount;

	public String getHailCount() {
		return hailCount;
	}

	public void setHailCount(Map<DateTime, Integer> hailCount) {
		
		this.hailCount = convertToJSONString(hailCount);;
	}

	public String getThunderstormCount() {
		return thunderstormCount;
	}

	public void setThunderstormCount(Map<DateTime, Integer> thunderstormCount) {
		
		this.thunderstormCount = convertToJSONString(thunderstormCount);
	}

	public String getTornadoCount() {
		return tornadoCount;
	}

	public void setTornadoCount(Map<DateTime, Integer> tornadoCount) {
		
		this.tornadoCount = convertToJSONString(tornadoCount);
	}

	public String getFlashfloodCount() {
		return flashfloodCount;
	}

	public void setFlashfloodCount(Map<DateTime, Integer> flashfloodCount) {
		try{
			for(DateTime d : flashfloodCount.keySet()){
		
			System.out.println(d + " : " + flashfloodCount.get(d) );
		}
		}catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		convertToJSONString(flashfloodCount);
		this.flashfloodCount = convertToJSONString(flashfloodCount);
	}
	
	private String convertToJSONString(Map<DateTime, Integer> map){
		DateTimeZone.setDefault(DateTimeZone.UTC);
		ObjectMapper obj = new ObjectMapper();
		try {
			obj.writeValueAsString(map);
			return obj.writeValueAsString(map);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
