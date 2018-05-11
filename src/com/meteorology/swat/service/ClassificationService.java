package com.meteorology.swat.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.meteorology.swat.DAO.ClassificationDAO;
import com.meteorology.swat.DAOImpl.ClassificationDAOImpl;
import com.meteorology.swat.bean.Classification;
import com.meteorology.swat.bean.Event;
import com.meteorology.swat.util.NormalizeHelper;

public class ClassificationService {
	
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
			classificationClass.setStartTime(keys[normalizeHelper.getStartIndex()]);
			classificationClass.setEndTime(keys[normalizeHelper.getEndIndex()]);
			classificationClass.setClassification(normalizeHelper.getCurrentClassification());
			//classificationClass.setCount(normalizeHelper.getCurrentClassificationCount());

			classID = classificationDAO.insert(classificationClass);

			//Setting the infoID and classID from the tables, for foreign key mapping
			Event eventModel = new Event();
			eventModel.setInfoId(infoID);
			eventModel.setClassificationId(classID);
			eventModelList.add(eventModel);
		}

		return eventModelList;
	}

}
