package com.meteorology.swat.DAO;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meteorology.swat.DAOImpl.ResultDAOImpl;
import com.meteorology.swat.bean.OverallResult;
import com.meteorology.swat.model.FilterForResult;

public interface ResultDAO {

	public void setDataSource() throws SQLException;
	
	
	
	public HashMap<String, BigInteger> filteredSelect(FilterForResult filterForResult);
	
	
	
	
	
	public List<Map<String, Object>> fetchAllResultsForHail();
	
	public List<Map<String, Object>> fetchAllResultsForThunderStormWind();
	
	public List<Map<String, Object>> fetchAllResultsForFlashflood();
	
	public List<Map<String, Object>> fetchAllResultsForTornado();
	
	public List<OverallResult> fetchAllResultsWithClassification(String morphology); 
	
	public List<OverallResult> fetchResultsWithClassificationAndState(String morphology, String state);
	
	
	
	//The real implementation with date, state and month filters!
	
	
	public boolean userHasResultAccess(String userEmail);
	
	public List<OverallResult> fetchAllResults();
	
	public List<OverallResult> fetchResultsWithState(String state);
	
	public List<OverallResult> fetchResultsWithMonth(String month);
	
	public List<OverallResult> fetchResultsWithDates(String from, String to);
	
	
	public List<OverallResult> fetchResultsWithStateMonth(String state, String month);
	
	public List<OverallResult> fetchResultsWithDateState(String from, String to, String state);
	
	public List<OverallResult> fetchResultsWithDateMonth(String from, String to, String month);
	
	
	public List<OverallResult> fetchResultsWithDateMonthState(String from, String to, String month, String state);
	
	/**
	 * A factory class to get the Implementation.
	 *
	 */
	public static class Factory {
		/**
		 * Create a {@link ResultDAO} object.
		 * @return a {@link ResultDAO} object.
		 */
		public ResultDAO create() {
			return new ResultDAOImpl();
		}
	}
	
	
}
