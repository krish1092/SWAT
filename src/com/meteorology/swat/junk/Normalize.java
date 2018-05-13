package com.meteorology.swat.junk;
/*package com.meteorology.swat.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meteorology.swat.model.ClassificationWithTimeRange;


public class Normalize {
	
	private TreeMap<String,String> orderedMap;
	
	public Normalize(TreeMap<String,String> orderedMap){
		this.orderedMap = orderedMap;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(Normalize.class);
	public void normalize()
	{
		
		boolean noConsecutiveThree = true;
		String currentClass, previousClass;
		
		String[] keys = orderedMap.keySet().toArray(new String[orderedMap.size()]);
		int count = 0;
		
		
		
		
		
		
		
		if(orderedMap.size()>1)
		{
			String startTime = orderedMap.firstKey(),endTime;
			for(int i=1;i<keys.length;i++)
			{
				currentClass = orderedMap.get(keys[i]);
				previousClass = orderedMap.get(keys[i-1]);
				
				if(currentClass.equalsIgnoreCase(previousClass) && i!=keys.length-1)
				{
					count++;
				} 
			
				else
				{
					if(i==keys.length-1)
					{
						endTime= keys[i];
						if(currentClass.equalsIgnoreCase(previousClass))  
						{
							count++;
							
						}
						else
						{
							
							count=1;
							startTime=endTime;
							
						}
						
						
					}
					else
					{
						endTime= keys[i];
						
						
						//Replacing previous with current values
						startTime=endTime;
						count=1;
					}
				}
			}
		}
		
		else
		{
			logger.info("User has classified for only one time instance");
		}
		
		
		
		
		
		
		
		
		String classI,classJ;
		
		//noThree- None of the classifications amount to more than 2 in total.
		//allMinThree- Every classification has at least 3 consecutive classification.
		boolean noConsecutiveThree=true,allMinThree=false;
		int totalCount=0;
		int k=0;
		int[] allCounts=new int[cswt.size()];
		
		
		for(int i=0;i<cswt.size();i++)
		{
			classI=cswt.get(i).getClassification();
			totalCount=cswt.get(i).getCount();
			
			if(noConsecutiveThree && cswt.get(i).getCount()>=3)
				noConsecutiveThree=false;
			
			for(int j=i+1;j<cswt.size();j++)
			{
				classJ=cswt.get(j).getClassification();
				
				if(noConsecutiveThree && cswt.get(j).getCount()>=3)
					noConsecutiveThree=false;
				
				if(classI.equalsIgnoreCase(classJ))
				{
					totalCount=totalCount+cswt.get(j).getCount();
				}
			}
			allCounts[k]=totalCount;
			k++;
		}
		
		Arrays.sort(allCounts);
		if(allCounts[0]<3) return null;
		else
		{
			//Huge gap in the count between the highest and the subsequent totalCounts
			if(allCounts[0]>3 && allCounts[1]<3 && allCounts[0]-allCounts[1]>3)
			{
				
			}
		}
		
		
		//return orderedMap;
	}
	
	
	public List<ClassificationWithTimeRange> orderList(TreeMap<String,String> timeAndClassTreeMap)
	{
		
		List<ClassificationWithTimeRange> orderedList=new ArrayList<ClassificationWithTimeRange>();
		String currentClass,previousClass;
		
		int count=1;
		
		String[] keys = timeAndClassTreeMap.keySet().toArray(new String[timeAndClassTreeMap.size()]);
		
		if(timeAndClassTreeMap.size()>1)
		{
			String startTime = timeAndClassTreeMap.firstKey(),endTime;
			for(int i=1;i<keys.length;i++)
			{
				currentClass = timeAndClassTreeMap.get(keys[i]);
				previousClass = timeAndClassTreeMap.get(keys[i-1]);
				
				if(currentClass.equalsIgnoreCase(previousClass) && i!=keys.length-1)
				{
					count++;
				} 
			
				else
				{
					if(i==keys.length-1)
					{
						endTime= keys[i];
						if(currentClass.equalsIgnoreCase(previousClass))  
						{
							count++;
							
						}
						else
						{
							
							//Putting previous class in the list.
							orderedList.add(makeClassificationWithTimeRange(startTime, endTime, count, previousClass));
							
							//Putting current Class in the list
							count=1;
							startTime=endTime;
							
						}
						
						orderedList.add(makeClassificationWithTimeRange(startTime, endTime, count, currentClass));
						
					}
					else
					{
						endTime= keys[i];
						
						//ClassificationWithTimeRange object that has the details of the current Classification
						orderedList.add(makeClassificationWithTimeRange(startTime, endTime, count, previousClass));
						
						//Replacing previous with current values
						startTime=endTime;
						count=1;
					}
				}
			}
		}
		
		else
		{
			logger.info("User has classified for only one time instance");
		}
		
		
		return orderedList;
		
	}

	
	
	public ClassificationWithTimeRange makeClassificationWithTimeRange(String startTime, String endTime, int count, String classification)
	{
		ClassificationWithTimeRange cwt= new ClassificationWithTimeRange();
		cwt.setClassification(classification);
		cwt.setCount(count);
		cwt.setStartTime(startTime);
		cwt.setEndTime(endTime);
		return cwt;
	}
	
}


*/