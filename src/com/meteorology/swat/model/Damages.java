package com.meteorology.swat.model;

import java.util.HashMap;


public class Damages {
	
	private Integer deathCount = 0, injuryCount = 0;
	private Long propertyDamage = (long) 0, cropDamage = (long) 0;
	

	public Integer getDeathCount() {
		return deathCount;
	}


	public void setDeathCount(Integer deathCount) {
		this.deathCount = deathCount;
	}


	public Integer getInjuryCount() {
		return injuryCount;
	}


	public void setInjuryCount(Integer injuryCount) {
		this.injuryCount = injuryCount;
	}


	public Long getPropertyDamage() {
		return propertyDamage;
	}


	public void setPropertyDamage(Long propertyDamage) {
		this.propertyDamage = propertyDamage;
	}


	public Long getCropDamage() {
		return cropDamage;
	}


	public void setCropDamage(Long cropDamage) {
		this.cropDamage = cropDamage;
	}


	public HashMap<String, Long> toMap()
	{
		HashMap<String, Long> map = new HashMap<String, Long> ();
		map.put("deathCount", deathCount.longValue());
		map.put("injuryCount", injuryCount.longValue());
		map.put("propertyDamage", propertyDamage);
		map.put("cropDamage", cropDamage);
		
		return  map;
	}
}
