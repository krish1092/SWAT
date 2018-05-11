package com.meteorology.swat.analytics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ExpertClassificationBeanResultSetExtractor implements ResultSetExtractor<List<ExpertClassification>> {

	@Override
	public List<ExpertClassification> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<ExpertClassification> list = new ArrayList<ExpertClassification>();
		ExpertClassification expertClassification ;
		while(rs.next())
		{
			expertClassification = new ExpertClassification();
			expertClassification.setClassification(rs.getString("classification"));
			expertClassification.setEmailAddress(rs.getString("email_Address"));
			list.add(expertClassification);
		}
		return list;
	}

}
