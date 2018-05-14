package com.meteorology.swat.util;

public class NormalizeHelper {
	
	//Start Index and End Index pertain to current classification's start and end values
	private int currentClassStartIndex;
	private int currentClassEndIndex;
	
	//Count of the current classification;
	private int currentClassificationCount;
	private String currentClassification;
	
	//Following are to track the same current classification located within the next 3 locations 
	private boolean currentClassWithinNextThreeLocations;
	private int nextIndexOfCurrentClass;
	private int nextCount;
	
	//Modified Triplet-becomes true when an artificial triplet is made.;
	private boolean modifiedTriplet;
	
	
	//Getters and Setters
	/**
	 * 
	 * @return indicates if the current classification is a modified triplet.
	 */
	public boolean isModifiedTriplet() {
		return modifiedTriplet;
	}
	
	/**
	 * 
	 * @param modifiedTriplet indicates if the current classification is a modified triplet.
	 * Default is false.
	 */
	public void setModifiedTriplet(boolean modifiedTriplet) {
		this.modifiedTriplet = modifiedTriplet;
	}
	
	/**
	 * 
	 * @return  The start index of the current classification in the sequence of classifications.
	 */
	public int getStartIndex() {
		return currentClassStartIndex;
	}
	
	/**
	 * 
	 * @param startIndex The start index of the current classification in the sequence of classifications.
	 */
	public void setStartIndex(int startIndex) {
		this.currentClassStartIndex = startIndex;
	}
	
	/**
	 * 
	 * @return The end index of the current classification in the sequence of classifications.
	 */
	public int getEndIndex() {
		return currentClassEndIndex;
	}
	
	/**
	 * 
	 * @param endIndex The end index of the current classification in the sequence of classifications.
	 */
	public void setEndIndex(int endIndex) {
		this.currentClassEndIndex = endIndex;
	}
	
	/**
	 * 
	 * @return The count of the classification in the current sequence.
	 */
	public int getCurrentClassificationCount() {
		return currentClassificationCount;
	}
	
	/**
	 * 
	 * @param currentClassificationCount The count of the classification in the current sequence.
	 */
	public void setCurrentClassificationCount(int currentClassificationCount) {
		this.currentClassificationCount = currentClassificationCount;
	}
	
	/**
	 * 
	 * @return The current classification.
	 */
	public String getCurrentClassification() {
		return currentClassification;
	}
	
	/**
	 * 
	 * @param currentClassification  The current classification.
	 */
	public void setCurrentClassification(String currentClassification) {
		this.currentClassification = currentClassification;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSameClassWithinNextThreeLocations() {
		return currentClassWithinNextThreeLocations;
	}
	
	/**
	 * 
	 * @param sameClassWithinNextThreeLocations
	 */
	public void setSameClassWithinNextThreeLocations(boolean sameClassWithinNextThreeLocations) {
		this.currentClassWithinNextThreeLocations = sameClassWithinNextThreeLocations;
	}
	
	/**
	 * 
	 * @return The next index of the current class.
	 */
	public int getNextIndexOfSameClass() {
		return nextIndexOfCurrentClass;
	}
	
	/**
	 * 
	 * @param nextIndexOfSameClass The next index of the current class.
	 */
	public void setNextIndexOfSameClass(int nextIndexOfSameClass) {
		this.nextIndexOfCurrentClass = nextIndexOfSameClass;
	}
	
	/**
	 * 
	 * @return The count of the classification in the next >=3 sequence.
	 */
	public int getNextCount() {
		return nextCount;
	}
	
	/**
	 * 
	 * @param nextCount The count of the classification in the next >=3 sequence.
	 */
	public void setNextCount(int nextCount) {
		this.nextCount = nextCount;
	}
	
}