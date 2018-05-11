package com.meteorology.swat.util;

public class NormalizeHelper {
	
	//Start Index and End Index pertain to current classification's start and end values
	private int currentClassStartIndex,currentClassEndIndex;
	
	//Count of the current classification;
	private int currentClassificationCount;
	private String currentClassification;
	
	//Following are to track the same current classification located within the next 3 locations 
	private boolean currentClassWithinNextThreeLocations;
	private int nextIndexOfCurrentClass, nextCount;
	
	//Modified Triplet-becomes true when an artificial triplet is made.;
	private boolean modifiedTriplet;
	
	
	//Getters and Setters
	public boolean isModifiedTriplet() {
		return modifiedTriplet;
	}
	public void setModifiedTriplet(boolean modifiedTriplet) {
		this.modifiedTriplet = modifiedTriplet;
	}
	public int getStartIndex() {
		return currentClassStartIndex;
	}
	public void setStartIndex(int startIndex) {
		this.currentClassStartIndex = startIndex;
	}
	public int getEndIndex() {
		return currentClassEndIndex;
	}
	public void setEndIndex(int endIndex) {
		this.currentClassEndIndex = endIndex;
	}
	public int getCurrentClassificationCount() {
		return currentClassificationCount;
	}
	public void setCurrentClassificationCount(int currentClassificationCount) {
		this.currentClassificationCount = currentClassificationCount;
	}
	public String getCurrentClassification() {
		return currentClassification;
	}
	public void setCurrentClassification(String currentClassification) {
		this.currentClassification = currentClassification;
	}
	public boolean isSameClassWithinNextThreeLocations() {
		return currentClassWithinNextThreeLocations;
	}
	public void setSameClassWithinNextThreeLocations(boolean sameClassWithinNextThreeLocations) {
		this.currentClassWithinNextThreeLocations = sameClassWithinNextThreeLocations;
	}
	public int getNextIndexOfSameClass() {
		return nextIndexOfCurrentClass;
	}
	public void setNextIndexOfSameClass(int nextIndexOfSameClass) {
		this.nextIndexOfCurrentClass = nextIndexOfSameClass;
	}
	public int getNextCount() {
		return nextCount;
	}
	public void setNextCount(int nextCount) {
		this.nextCount = nextCount;
	}
	
}