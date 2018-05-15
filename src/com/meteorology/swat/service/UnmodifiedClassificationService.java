package com.meteorology.swat.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.meteorology.swat.DAO.UnmodifiedClassificationDAO;
import com.meteorology.swat.DAOImpl.UnmodifiedClassificationDAOImpl;
import com.meteorology.swat.bean.UnmodifiedClassification;

/**
 * A class to handle the unmodified classifications (as entered by the user).
 * @author Krishnan Subramanian
 *
 */
public class UnmodifiedClassificationService {
	
	private TreeMap<String, String> timeAndClassTreeMap;
	private BigInteger infoID;
	private List<UnmodifiedClassification> unmodifiedClassificationList;
	
	/**
	 * Constructor.
	 * @param timeAndClassTreeMap An ordered map of time and class.
	 * @param infoID Information id.
	 */
	public UnmodifiedClassificationService(TreeMap<String,String> timeAndClassTreeMap, BigInteger infoID){
		
		this.timeAndClassTreeMap = timeAndClassTreeMap;
		this.infoID = infoID;
	}
	
	/**
	 * Construct an unmodified list.
	 */
	private void makeUnmodifiedList(){
		
		UnmodifiedClassification unmodifiedClassification;
		unmodifiedClassificationList = new ArrayList<>();
		
		for(String key : timeAndClassTreeMap.keySet())
		{
			unmodifiedClassification = new UnmodifiedClassification();
			unmodifiedClassification.setInfoID(infoID);
			unmodifiedClassification.setClassification(timeAndClassTreeMap.get(key));
			unmodifiedClassification.setDateTime(key);
			unmodifiedClassificationList.add(unmodifiedClassification);	
		}
		
	}
	
	/**
	 * Store the unmodified list into the database.
	 */
	public void insertIntoUnmodifiedClassification(){
		makeUnmodifiedList();
		UnmodifiedClassificationDAO unmodifiedClassificationDAO = new UnmodifiedClassificationDAOImpl();
		unmodifiedClassificationDAO.setDataSource();
		unmodifiedClassificationDAO.insertIntoUnmodifiedClassification(unmodifiedClassificationList);
	}
}
