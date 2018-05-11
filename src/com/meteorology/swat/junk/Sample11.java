package com.meteorology.swat.junk;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

public class Sample11 {
	
	public static void main(String[] args) {
		
		Map<DateTime, Integer> map = new TreeMap<>();
		
		DateTimeZone.setDefault(DateTimeZone.UTC);
		new DateTime();
		map.put(DateTime.parse("2015-06-12 18:30:00", 
				DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")),3);
		map.put(DateTime.parse("2015-06-12 17:00:00", 
				DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")),4);
		map.put(DateTime.parse("2015-06-01 17:00:00", 
				DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")),6);
		
		for(DateTime d: map.keySet()){
			System.out.println(d+":"+ map.get(d));
		}
		System.out.println("---------");
		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
