package com.meteorology.swat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Normalizer {


	private static final Logger logger = LoggerFactory.getLogger(Normalizer.class);

	private boolean modifiedTriplet = false;

	private String[] keys;
	
	public HashMap<Date,String> getNormalizedDateAndClassMap(List<NormalizeHelper> normalizeHelperList){
		HashMap<Date, String> normalizedMap = new HashMap<Date, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HHmm");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date d;
		try {
			for(NormalizeHelper n : normalizeHelperList)
			{
				d = sdf.parse(keys[n.getStartIndex()]);
				normalizedMap.put(d, n.getCurrentClassification());
			}
				
				
			} catch (ParseException e) {
				e.printStackTrace();
		}
		return normalizedMap;
	}

	public String[] getKeys() {
		return keys;
	}

	public boolean isNoNaturalTriplet(List<NormalizeHelper> normalizeHelperList) {
		for(NormalizeHelper n: normalizeHelperList)
		{
			if(n.getCurrentClassificationCount() >= 3)
				return false;
		}
		return true;
	}

	public boolean isModifiedTriplet() {
		return modifiedTriplet;
	}

	public List<NormalizeHelper> normalize(List<NormalizeHelper> normalizeHelperList)
	{

		NormalizeHelper	currentNormalizeHelper,nextNormalizeHelper ;
		int i=0;

		while(i + 1 < normalizeHelperList.size() )
		{
			currentNormalizeHelper = normalizeHelperList.get(i);
			int currentIndex = i,nextIndex = i + 1;
			nextNormalizeHelper = normalizeHelperList.get(nextIndex);

			//Long List of conditions checked starts here

			if (nextNormalizeHelper != null) {

				//If the current and next classes are same, merge them into one.
				if(currentNormalizeHelper.getCurrentClassification().equalsIgnoreCase(nextNormalizeHelper.getCurrentClassification()))
				{
					//Merge the two classes into one since both are same classification
					currentNormalizeHelper = merge(currentNormalizeHelper, nextNormalizeHelper);
					currentNormalizeHelper.setModifiedTriplet(true);
					
					//Setting the value to currentIndex
					normalizeHelperList.set(currentIndex, currentNormalizeHelper);

					//Remove the nextNormalizeHelper from the list
					normalizeHelperList.remove(nextIndex);

				}




				//If the CURRENT CLASS COUNT >3 and NEXT CLASS COUNT > 3 , we can safely move to the next class for normalization.
				else	if (currentNormalizeHelper.getCurrentClassificationCount() >= 3
						&& nextNormalizeHelper.getCurrentClassificationCount() >= 3) {
					/*
					 * This is already normalized and in the form we expect. Eg: BE,BE,BE, NS,NS,NS
					 * */
					i++;
				}

				//CURRENT CLASSIFICATION COUNT >=3 and NEXT CLASSIFICATION COUNT <= 3. This leads to 4 sub conditions
				else if (currentNormalizeHelper.getCurrentClassificationCount() >= 3
						&& nextNormalizeHelper.getCurrentClassificationCount() < 3 ) {

					if(!currentNormalizeHelper.isModifiedTriplet())
					{
						if (currentNormalizeHelper.isSameClassWithinNextThreeLocations()
								&& nextNormalizeHelper.isSameClassWithinNextThreeLocations()) {
							//NextOfnext
							//In this case, preference is given to the dominant current class over the next class which has a subsequent presence
							//Convert Both the next class and its next location to the current Class if the next Of next location count < 2;
							// Else convert next location of current class to the next class


							if(nextNormalizeHelper.getNextCount() == currentNormalizeHelper.getCurrentClassificationCount())
							{
								//Merge the next with the Current
								//Merge the current.next with next.next

								//i.e Swap the next and current.next

								//Eg:BE,BE,BE, NS, BE, NS, NS, NS ==> BE,BE,BE,BE , NS,NS,NS,NS (BE,NS are swapped positions)

								normalizeHelperList = swap(normalizeHelperList, currentIndex, nextIndex);

								//This is a modified triplet. Hence change the modifiedTriplet value to true;
								modifiedTriplet = true;

								//The list has changed, we need to normalize again
								normalizeHelperList = normalize(normalizeHelperList);
								return normalizeHelperList;

							}
							else if(nextNormalizeHelper.getNextCount() > currentNormalizeHelper.getCurrentClassificationCount())
							{
								//Eg: BE,BE,BE,ns,be,NS,NS,NS,NS ==> BE,BE,BE,ns,ns,NS,NS,NS,NS
								//Convert the next location of CurrentClass to the next Class and merge
								int nextIndexOfCurrentClass = nextIndex + 1;
								NormalizeHelper nextCurrentNormalizeHelper = normalizeHelperList.get(nextIndexOfCurrentClass);

								int nextIndexOfnextClass = nextIndexOfCurrentClass + 1;
								NormalizeHelper nextNextNormalizeHelper = normalizeHelperList.get(nextIndexOfnextClass);
								//Merge the current class with the next class with dominant sequence 
								nextNextNormalizeHelper = merge(nextCurrentNormalizeHelper, nextNextNormalizeHelper);

								//merge the next class with its nextNext Class . Here, next class and nextNextClass are equal
								nextNormalizeHelper = merge(nextNormalizeHelper, nextNextNormalizeHelper);
								nextNormalizeHelper = searchNext(nextNormalizeHelper, nextIndexOfnextClass, normalizeHelperList);
								
								//This is a modified Triplet - Set it true to prevent further formation of triplet of same class
								nextNormalizeHelper.setModifiedTriplet(true);
								modifiedTriplet = true;
								
								normalizeHelperList.set(nextIndex, nextNormalizeHelper);

								//To be removed from the list - nextIndex and nextIndexOfCurrentClass
								//Now that the classes have been swapped, we can remove their individual existence.
								normalizeHelperList.remove(nextIndexOfnextClass);
								normalizeHelperList.remove(nextIndexOfCurrentClass);

								//The list has changed, we need to normalize again
								normalizeHelperList = normalize(normalizeHelperList);
								return normalizeHelperList;
							} 
							else if(nextNormalizeHelper.getNextCount() < currentNormalizeHelper.getCurrentClassificationCount())
							{
								//merge only the next normalize helper.
								int nextIndexOfCurrentClass = nextIndex + 1;
								NormalizeHelper nextCurrentNormalizeHelper = normalizeHelperList.get(nextIndexOfCurrentClass);


								/*Eg: 1) BE,BE,BE,BE,ns,BE,NS,NS,NS ==> BE,BE,BE,BE,be,BE,NS,NS,NS
								 * 	  2) BE,BE,BE,BE,ns,BE,NS,... ==> BE,BE,BE,BE,be,BE,NS,... -> The imperfections will be weeded out
								 * 		by gallusNormalization method
								 */
								currentNormalizeHelper = merge(currentNormalizeHelper, nextNormalizeHelper);
								currentNormalizeHelper = merge(currentNormalizeHelper, nextCurrentNormalizeHelper);

								currentNormalizeHelper.setSameClassWithinNextThreeLocations(false);
								currentNormalizeHelper.setNextCount(0);
								currentNormalizeHelper.setNextIndexOfSameClass(-1);
								
								//This is a modified Triplet - Set it true to prevent further formation of triplet of same class
								currentNormalizeHelper.setModifiedTriplet(true);
								modifiedTriplet = true;

								normalizeHelperList.set(currentIndex, currentNormalizeHelper);

								normalizeHelperList.remove(nextIndexOfCurrentClass);
								normalizeHelperList.remove(nextIndex);

								//The list has changed, we need to normalize again
								normalizeHelperList = normalize(normalizeHelperList);
								return normalizeHelperList;

							}

						} else if (/*currentNormalizeHelper.isSameClassWithinNextThreeLocations()
								&&*/ !nextNormalizeHelper.isSameClassWithinNextThreeLocations()) {

							/*  This condition is equivalent to the following two conditions put together
								(!currentNormalizeHelper.isSameClassWithinNextThreeLocations() && !nextNormalizeHelper.isSameClassWithinNextThreeLocations())
							 	OR (currentNormalizeHelper.isSameClassWithinNextThreeLocations() && !nextNormalizeHelper.isSameClassWithinNextThreeLocations()) */

							/*Eg: 1) BE,BE,BE,ns,BE,CC.... ==> BE,BE,BE,be,BE,CC....
							 * 
							 * 
							 */

							/*The next class has a count less than 3 & No further values within next three. Therefore, convert the next Class to current class.
							  i.e, Merge the current and next classes */
							currentNormalizeHelper = merge(currentNormalizeHelper, nextNormalizeHelper);

							//Search if the current Class is present the subsequent indices of the next Class which has been merged now.
							currentNormalizeHelper = searchNext(currentNormalizeHelper, currentIndex, normalizeHelperList);
							
							//This is a modified Triplet - Set it true to prevent further formation of triplet of same class
							currentNormalizeHelper.setModifiedTriplet(true);
							modifiedTriplet = true;
							
							normalizeHelperList.set(currentIndex, currentNormalizeHelper);
							normalizeHelperList.remove(nextIndex);

							//i=0;
							//Now, the list has changed and we need to normalize again.
							normalizeHelperList = normalize(normalizeHelperList);
							return normalizeHelperList;

						} else if (!currentNormalizeHelper.isSameClassWithinNextThreeLocations()
								&& nextNormalizeHelper.isSameClassWithinNextThreeLocations()) {
							/*Eg: BE,BE,BE,NS,CC,NS,NS.... ==> Only the current pointer needs to move to next location
							 * as nothing needs to be done here.
							 */


							/*The current class does not have a subsequent presence within the next three. However, the next class has subsequent presence. Hence
							  we can move the index to the next class.*/
							i++;
						}

					}
					else
					{
						i++;
					}
					
				}

				//CURRENT CLASS COUNT < 3 but NEXT CLASS COUNT >= 3
				else if (currentNormalizeHelper.getCurrentClassificationCount() < 3
						&& nextNormalizeHelper.getCurrentClassificationCount() >= 3) {
					
					if(!nextNormalizeHelper.isModifiedTriplet())
					{
						/* The following conditions are not possible
						   1) currentNormalizeHelper.isSameClassWithinNextThreeLocations() && nextNormalizeHelper.isSameClassWithinNextThreeLocations()
						   2) currentNormalizeHelper.isSameClassWithinNextThreeLocations() && !nextNormalizeHelper.isSameClassWithinNextThreeLocations()
						   because, if CURRENT CLASS COUNT  < 3, and NEXT CLASS COUNT >=3, then current.SameClassWithinThree can NOT be true.*/

						/* The following condition is equivalent to 
						  (!currentNormalizeHelper.isSameClassWithinNextThreeLocations() && nextNormalizeHelper.isSameClassWithinNextThreeLocations()) 
						  OR
						  (!currentNormalizeHelper.isSameClassWithinNextThreeLocations() && !nextNormalizeHelper.isSameClassWithinNextThreeLocations())
						  Since, current.SameClassWithinThree can NOT be true, the only possibility is current.SameClassWithinThree can ONLY BE false.
						  Hence, there is no further condition involved here.
						 */

						/* In this case, simply convert the current class to the next class since CURRENT CLASS COUNT < 3 and followed by a dominant
						   next class with NEXT CLASS COUNT >= 3 
						 */

						/*
						 * Eg: BE,BE, NS,NS,NS,.... ==> NS,NS,NS,NS,NS....
						 * 
						 */

						nextNormalizeHelper = merge(nextNormalizeHelper, currentNormalizeHelper);
						
						//This is a modified Triplet - Set it true to prevent further formation of triplet of same class
						nextNormalizeHelper.setModifiedTriplet(true);
						modifiedTriplet = true;
						
						//Set the nextNormalizeHelper value to the latest one.
						normalizeHelperList.set(nextIndex, nextNormalizeHelper);

						//Remove the current Class from the list
						normalizeHelperList.remove(currentIndex);
						//Now, the list has changed and we need to normalize again.

						normalizeHelperList = normalize(normalizeHelperList);
						return normalizeHelperList;
					}
					else
					{
						i++;
					}
					

				} 
				//CURRENT CLASS COUNT <3 and NEXT CLASS COUNT < 3
				else if (currentNormalizeHelper.getCurrentClassificationCount() < 3
						&& nextNormalizeHelper.getCurrentClassificationCount() < 3) {


					if (currentNormalizeHelper.isSameClassWithinNextThreeLocations()
							&& nextNormalizeHelper.isSameClassWithinNextThreeLocations()) {

						if(nextNormalizeHelper.getNextCount() >= 3
								||
								(nextNormalizeHelper.getNextCount() < 3 
										&& 
										currentNormalizeHelper.getCurrentClassificationCount() < nextNormalizeHelper.getNextCount())
								)
						{
							/*Eg: 1)BE,NS,BE,NS,NS,NS 
							 * 2)BE,NS,BE,NS,NS 
							 * In either case,increment the current pointer and it becomes a condition already solved.
							 */
							i++;
						}

						else if(currentNormalizeHelper.getCurrentClassificationCount() 
								==	nextNormalizeHelper.getNextCount())
							if(currentNormalizeHelper.getCurrentClassificationCount() > 1)
							{
								//Swap (next, current.next )
								//Eg: BE,BE,NS,BE,NS,NS

								//Merge the next with the Current
								//Merge the current.next with next.next

								normalizeHelperList = swap(normalizeHelperList, currentIndex, nextIndex);

								//This is a modified triplet. Hence change the modifiedTriplet value to true;
								modifiedTriplet = true;

								//The list has changed, we need to normalize again
								normalizeHelperList = normalize(normalizeHelperList);
								return normalizeHelperList;
							}
							else
							{
								//Eg: LS,NS,LS,NS,.. ==> Increment the pointer as nothing can be decided at this point
								i++;
							}

						else if(currentNormalizeHelper.getCurrentClassificationCount() > nextNormalizeHelper.getNextCount() || 
								(nextNormalizeHelper.getNextCount() < 3 
										&& 
										currentNormalizeHelper.getCurrentClassificationCount() == nextNormalizeHelper.getNextCount()))
						{
							/* Merge the next Class to current Class to form a triplet.
							 * Eg: 1) BE,BE,NS,BE,NS,....
							 * 2)BE,NS,BE,NS,....
							 */
							//Merge next and current Classes
							currentNormalizeHelper = merge(currentNormalizeHelper, nextNormalizeHelper);

							//Search if the current Class is present the subsequent indices of the next Class which has been merged now.
							currentNormalizeHelper = searchNext(currentNormalizeHelper, currentIndex, normalizeHelperList);
							
							
							//This is a modified Triplet - Set it true to prevent further formation of triplet of same class
							currentNormalizeHelper.setModifiedTriplet(true);
							modifiedTriplet = true;
							
							normalizeHelperList.set(currentIndex, currentNormalizeHelper);
							normalizeHelperList.remove(nextIndex);

							//This is a modified triplet. Hence change the modifiedTriplet value to true;
							

							//Now, the list has changed and we need to normalize again.
							normalizeHelperList = normalize(normalizeHelperList);
							return normalizeHelperList;

						}

					} else if (currentNormalizeHelper.isSameClassWithinNextThreeLocations()
							&& !nextNormalizeHelper.isSameClassWithinNextThreeLocations()) {

						if(currentNormalizeHelper.getCurrentClassificationCount() >= 2 || currentNormalizeHelper.getNextCount() >= 2)
						{
							/* Here, the current class has 2 values and the next class is the odd one out. Hence, the next class
							   can be merged with the current class.
							 */
							/*
							 * Eg: BE,NS,BE,BE... ==> BE,BE,BE,BE....
							 */
							currentNormalizeHelper = merge (currentNormalizeHelper, nextNormalizeHelper);

							int nextCurrentIndex = nextIndex + 1;
							NormalizeHelper nextCurrentNormalizeHelper = normalizeHelperList.get(nextCurrentIndex);
							currentNormalizeHelper = merge (currentNormalizeHelper, nextCurrentNormalizeHelper);

							//This is a modified Triplet - Set it true to prevent further formation of triplet of same class
							currentNormalizeHelper.setModifiedTriplet(true);
							modifiedTriplet = true;
							
							//Setting the currentNormalizeHelper in the list
							normalizeHelperList.set(currentIndex, currentNormalizeHelper);

							//Remove the nextCurrent  and next Index
							normalizeHelperList.remove(nextCurrentIndex);
							normalizeHelperList.remove(nextIndex);

							//The list has changed. Normalize again
							normalizeHelperList = normalize(normalizeHelperList);

							return normalizeHelperList;
						}
						else
						{
							/*
							 * Eg: BE,IC,BE remains that way.
							 * 
							 */
							i++;
						}


					} else if (!currentNormalizeHelper.isSameClassWithinNextThreeLocations()
							&& nextNormalizeHelper.isSameClassWithinNextThreeLocations()) {

						/* Here, the current class is the odd one out as the next class . Hence, the current class
						   can be merged with the next class.
						 */

						/*
						 * Eg: 1) BE,NS,CC,NS,NS,NS,.... 
						 * 	   2) BE,NS,CC,NS,CC,....
						 * 	   3) BE,NS,CC,NS,IC,
						 * All the above cases will be handled at different conditions if we increment the current pointer. 
						 * We can simply move the current pointer to the subsequent index. 
						 * The imperfections will be corrected by gallusNormalization.
						 */
						i++;

					} else if (!currentNormalizeHelper.isSameClassWithinNextThreeLocations()
							&& !nextNormalizeHelper.isSameClassWithinNextThreeLocations()) {

						/*Not much can be done at this stage and this condition,if occurs, will be normalized by
						 * gallusNormalization.
						 * 
						 * Eg: 
						 * 1) BE,NS,CC,CC,....
						 * 2) BE,BE,NS,CC,CC....
						 * 3) BE,BE,NS,NS,CC,CC,....
						 * 4) BE,NS,NS,CC,IC,.....
						 */

						i++;
					}

				} 
			}

		}

		return normalizeHelperList;
	}

	public List<NormalizeHelper> gallusNormalization(List<NormalizeHelper> normalizeHelperList)
	{
		List<NormalizeHelper> gallusList = new ArrayList<NormalizeHelper>();
		int firstTripletIndex = firstTripletIndex(normalizeHelperList);
		if(firstTripletIndex == -1)
			//There are no triplets in the normalized Sequence ,meaning, the sequence can not be normalized.
			logger.info("No Triplets in the sequence, Can not be normalized further");
		else
		{
			//In this case there are single/multiple triplets. 
			//Now, we can divide the sequence by the number of triplets and construct 
			// the sequence into groups of each class.
			int startIndex = 0,count = 0;
			int totalCount = getTotalCount(normalizeHelperList);

			List<Integer> tripletsIndices = getTripletIndices(normalizeHelperList);
			int countOfTriplets = tripletsIndices.size();

			int maxCountIndex = getMaxCountIndex(normalizeHelperList,tripletsIndices);
			int divisionOfSequence =  totalCount/countOfTriplets;
			int remainderOfDivision = totalCount % countOfTriplets;

			int endIndex = divisionOfSequence;
			NormalizeHelper temp; 
			for(int i : tripletsIndices)
			{
				temp = normalizeHelperList.get(i);
				endIndex = startIndex + divisionOfSequence - 1;
				count =  endIndex-startIndex + 1;
				if(remainderOfDivision > 0 && i == maxCountIndex)
				{
					//Giving the index with the maximum class count a higher count in the sequence
					endIndex++;
					count++;
				}

				gallusList.add(
						makeNormalizeHelper(temp.getCurrentClassification(), 
								startIndex, endIndex, count, 
								false, -1, 0)
						);
				startIndex = endIndex + 1;
			}
		}
		return gallusList;
	}


	public boolean allThree(List<NormalizeHelper> normalizeHelperList)
	{
		for(NormalizeHelper n : normalizeHelperList)
			if(n.getCurrentClassificationCount() < 3)
				return false;

		return true;
	}

	public int getMaxCountIndex(List<NormalizeHelper> normalizeHelperList, List<Integer> tripletIndices)
	{
		int firstTriplet = tripletIndices.get(0);
		int maxCountIndex = firstTriplet;
		for(int i =0; i<tripletIndices.size();i++)
		{
			int index = tripletIndices.get(i);
			//If some index i has a class that is greater in count that maxCountIndex's class, assign the new value to maxCountIndex
			if(normalizeHelperList.get(index).getCurrentClassificationCount() >= normalizeHelperList.get(maxCountIndex).getCurrentClassificationCount())
				maxCountIndex = index;
		}
		return maxCountIndex;
	}

	public List<Integer> getTripletIndices(List<NormalizeHelper> normalizeHelperList)
	{
		List<Integer> triplets = new ArrayList<Integer>();
		for(int i=0; i<normalizeHelperList.size();i++)
		{
			if(normalizeHelperList.get(i).getCurrentClassificationCount() >= 3)
				triplets.add(i);
		}
		return triplets;
	}

	public int getSumOfTripletCount(List<NormalizeHelper> normalizeHelperList)
	{
		int sumOfTripletCount = 0;
		for(NormalizeHelper n : normalizeHelperList)
			if(n.getCurrentClassificationCount() >= 3)
				sumOfTripletCount = sumOfTripletCount + n.getCurrentClassificationCount();
		return sumOfTripletCount;

	}

	public int getTotalCount(List<NormalizeHelper> normalizeHelperList)
	{
		int totalCount = 0;
		for(NormalizeHelper n : normalizeHelperList)
			totalCount = totalCount + n.getCurrentClassificationCount();
		return totalCount;
	}

	public int firstTripletIndex(List<NormalizeHelper> normalizeHelperList)
	{
		for(int i=0; i<normalizeHelperList.size(); i++)
		{
			if(normalizeHelperList.get(i).getCurrentClassificationCount() >=3)
				return i;
		}

		return -1;
	}

	public NormalizeHelper merge(NormalizeHelper first, NormalizeHelper second)
	{
		//creating a new NormalizeHelper object with merged values 
		NormalizeHelper merged = new NormalizeHelper();

		// Adding the the classes' counts
		int mergedCurrentClassificationCount = first.getCurrentClassificationCount() + second.getCurrentClassificationCount() ;

		// Either the first class count is applied to the second class or vice verse. three possible conditions
		// Also, if both the classes are same, then the following conditions still suffice to merge
		if(first.getCurrentClassificationCount() == second.getCurrentClassificationCount())
		{
			if(first.isSameClassWithinNextThreeLocations())
				merged.setCurrentClassification(first.getCurrentClassification());
			else if(second.isSameClassWithinNextThreeLocations())
				merged.setCurrentClassification(second.getCurrentClassification());
			else
				merged.setCurrentClassification(first.getCurrentClassification());

		}
		else if(first.getCurrentClassificationCount() > second.getCurrentClassificationCount())
			merged.setCurrentClassification(first.getCurrentClassification());
		else
			merged.setCurrentClassification(second.getCurrentClassification());

		//setting Added count
		merged.setCurrentClassificationCount(mergedCurrentClassificationCount);

		//setting the start and end indices
		merged.setStartIndex(first.getStartIndex());
		merged.setEndIndex(second.getEndIndex());	

		return merged;
	}

	public List<NormalizeHelper> swap(List<NormalizeHelper> normalizeHelperList, int currentIndex, int nextIndex)
	{
		NormalizeHelper currentNormalizeHelper = normalizeHelperList.get(currentIndex),
				nextNormalizeHelper = normalizeHelperList.get(nextIndex);
		//next Indices of the current and next classes
		int nextIndexOfCurrentClass = nextIndex + 1;
		int nextIndexOfnextClass = nextIndexOfCurrentClass + 1;


		//Swap the current.next and next objects
		Collections.swap(normalizeHelperList, nextIndex, nextIndexOfCurrentClass);

		//merging the current and next class- they have been swapped already, indicating, the current and next classes are the same
		currentNormalizeHelper = merge(currentNormalizeHelper, nextNormalizeHelper);

		//Merge the nextIndexOfCurrentClass with nextIndexOfNextClass
		NormalizeHelper nextCurrentNormalizeHelper = normalizeHelperList.get(nextIndexOfCurrentClass);
		NormalizeHelper nextNextNormalizeHelper = normalizeHelperList.get(nextIndexOfnextClass);
		nextNormalizeHelper = merge(nextCurrentNormalizeHelper,	nextNextNormalizeHelper);

		//in the merged sequence, current class is NOT present within three locations since it is occupied by the next class
		currentNormalizeHelper.setSameClassWithinNextThreeLocations(false);
		currentNormalizeHelper.setNextIndexOfSameClass(-1);
		currentNormalizeHelper.setNextCount(0);

		//Search if the next class is present in the subsequent sequence.
		nextNormalizeHelper = searchNext(nextNormalizeHelper, nextIndexOfnextClass, normalizeHelperList);

		//This is a modified Triplet - Set it true to prevent further formation of triplet of same class
		currentNormalizeHelper.setModifiedTriplet(true);
		nextNormalizeHelper.setModifiedTriplet(true);
		
		normalizeHelperList.set(currentIndex, currentNormalizeHelper);
		normalizeHelperList.set(nextIndexOfnextClass, nextNormalizeHelper);

		//To be removed from the list - nextIndex and nextIndexOfCurrentClass
		//Now that the classes have been swapped, we can remove their individual existence.
		normalizeHelperList.remove(nextIndexOfCurrentClass);
		normalizeHelperList.remove(nextIndex);

		return normalizeHelperList;

	}

	public NormalizeHelper searchNext(NormalizeHelper currentNormalizeHelper, int currentIndex, List<NormalizeHelper> normalizeHelperList)
	{
		boolean currentClassWithinNextThreeLocations = false;
		int nextCount=0,nextIndexOfSameClass=-1;
		String currentClass = currentNormalizeHelper.getCurrentClassification();

		for(int i = 1; (i < 3 && (currentIndex + i) < normalizeHelperList.size()); i++)
		{
			NormalizeHelper next = normalizeHelperList.get(currentIndex+i);
			String nextClass = next.getCurrentClassification();

			if( currentClass.equalsIgnoreCase(nextClass))
			{
				currentClassWithinNextThreeLocations = true;
				currentNormalizeHelper.setSameClassWithinNextThreeLocations(currentClassWithinNextThreeLocations);

				nextCount = next.getNextCount();
				currentNormalizeHelper.setNextCount(nextCount);

				nextIndexOfSameClass = next.getNextIndexOfSameClass();
				currentNormalizeHelper.setNextIndexOfSameClass(nextIndexOfSameClass);

				return currentNormalizeHelper;
			}

		}

		currentNormalizeHelper.setSameClassWithinNextThreeLocations(currentClassWithinNextThreeLocations);
		currentNormalizeHelper.setNextCount(nextCount);
		currentNormalizeHelper.setNextIndexOfSameClass(nextIndexOfSameClass);
		return currentNormalizeHelper;
	}



	// Helper methods to Make a NormalizeHelper List.

	public List<NormalizeHelper> makeList(TreeMap<String, String> timeAndClassTreeMap)
	{


		//Start Index and End Index pertain to current classification's start and end values
		int currentClassStartIndex=0,currentClassEndIndex=0;

		//Count of the current classification;
		int currentClassificationCount=1;
		String currentClassification;

		//Following are to track the same current classification located within the next 3 locations 
		boolean currentClassWithinNextThreeLocations = false;
		int nextIndexOfCurrentClass=-1, nextCurrentClassCount=0;

		List<NormalizeHelper> normalizerList=new ArrayList<NormalizeHelper>();
		String nextClassification;
		int nextClassIndex;


		keys = timeAndClassTreeMap.keySet().toArray(new String[timeAndClassTreeMap.size()]);

		if(timeAndClassTreeMap.size()>1)
		{
			for(int i=0;i< keys.length ;i++)
			{
				//Getting current and previous classes
				currentClassification = timeAndClassTreeMap.get(keys[i]);
				try{
					nextClassification = timeAndClassTreeMap.get(keys[i+1]);

				}catch(ArrayIndexOutOfBoundsException e)
				{
					nextClassification = null;
				}

				nextClassIndex = i+1;

				// Check if the next class is the same as the current class
				if(nextClassification!=null && nextClassification.equalsIgnoreCase(currentClassification))
				{
					//increase the current class count
					currentClassificationCount++;
				}
				else 
				{
					//Marking the end of the current class since the next class is not the same as the current one.
					currentClassEndIndex = i;

					for(int j=2; j <= 2 && (i+j) < keys.length; j++)
					{
						String currentClassificationWithinNextThree = timeAndClassTreeMap.get(keys[i + j]);
						if(currentClassification.equalsIgnoreCase(currentClassificationWithinNextThree))
						{
							//Locating the next location of the current classification
							currentClassWithinNextThreeLocations = true;
							nextIndexOfCurrentClass = i + j;
							nextCurrentClassCount = 1;
							for(int k=1;(i+j+k) < keys.length; k++)
							{
								//Count the next set of current classification
								String nextOfNextCurrentClassification = timeAndClassTreeMap.get(keys[i+j+k]);
								if(nextOfNextCurrentClassification.equals(currentClassification))
									nextCurrentClassCount++;
								else
									break;
							}
							break;
						}
					}

					normalizerList.add(makeNormalizeHelper(currentClassification, currentClassStartIndex, currentClassEndIndex, currentClassificationCount, 
							currentClassWithinNextThreeLocations, nextIndexOfCurrentClass, nextCurrentClassCount));

					//Replacing current values with next one.
					currentClassStartIndex =  nextClassIndex;
					currentClassificationCount = 1;

					currentClassWithinNextThreeLocations = false;
					nextIndexOfCurrentClass = -1;
					nextCurrentClassCount = 0;
				}
			}
		}

		else
		{
			logger.info("User has classified for only one time instance");
		}

		return normalizerList;
	}


	public NormalizeHelper makeNormalizeHelper(String currentClassification,int currentClassStartIndex,
			int currentClassEndIndex, int currentClassificationCount,
			/* Next location of same class within 3 locations*/
			boolean sameClassWithinNextThreeLocations,int nextIndexOfSameClass, int nextCount)
	{
		NormalizeHelper nh= new NormalizeHelper();

		//CurrentClass
		nh.setCurrentClassification(currentClassification);
		nh.setStartIndex(currentClassStartIndex);
		nh.setEndIndex(currentClassEndIndex);
		nh.setCurrentClassificationCount(currentClassificationCount);

		//Irrespective of whether the current count is > 2 or not, modifiedTriplet is by default false.
		nh.setModifiedTriplet(false);

		//next Location of currentClass
		nh.setSameClassWithinNextThreeLocations(sameClassWithinNextThreeLocations);
		nh.setNextIndexOfSameClass(nextIndexOfSameClass);
		nh.setNextCount(nextCount);
		return nh;
	}


}
