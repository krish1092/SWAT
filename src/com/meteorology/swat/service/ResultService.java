package com.meteorology.swat.service;


import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meteorology.swat.DAO.ResultDAO;
import com.meteorology.swat.DAOImpl.ResultDAOImpl;
import com.meteorology.swat.bean.OverallResult;
import com.meteorology.swat.model.FilterForResult;

/**
 * A class to handle analytics related operations.
 * @author Krishnan Subramanian
 *
 */
public class ResultService {

	private FilterForResult filterForResult;
	
	/**
	 * Constructor that takes a filter as argument.
	 * @param filterForResult
	 */
	public ResultService(FilterForResult filterForResult){
		this.filterForResult = filterForResult;
	}
	
	/**
	 * Default constructor.
	 */
	public ResultService(){
		
	}
		
	/**
	 * Checks if the user has access to the results page.
	 * @param userEmail The email address of the user.
	 * @return true or false.
	 * @throws SQLException If there's an issue connecting to the database.
	 */
	public boolean userHasResultAccess(String userEmail) throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		
		boolean userHasAccess = resultDAO.userHasResultAccess(userEmail);
		return userHasAccess;
		
	}
	
	/**
	 * Get all the results for the results home page.
	 * @return A list of results.
	 * @throws SQLException  If there's an issue connecting to the database.
	 */
	public List<OverallResult> getOverallResult() throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchAllResults();
		return list;
	}
	
	/**
	 * Get all the results for a given US state.
	 * @param state The US state to fetch the results for.
	 * @return The results for the US state.
	 * @throws SQLException If there's an issue connecting to the database.
	 */
	public List<OverallResult> fetchResultsWithState(String state)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithState(state);
		return list;
	}
	
	/**
	 * Get all the results for a given date window.
	 * @param from The from date.
	 * @param to The to date.
	 * @return The results for the given date window.
	 * @throws SQLException If there's an issue connecting to the database.
	 */
	public List<OverallResult> fetchResultsWithDates(String from, String to)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithDates(from, to);
		return list;
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 * @throws SQLException If there's an issue connecting to the database.
	 */
	public List<OverallResult> fetchResultsWithMonth(String month)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithMonth(month);
		return list;
	}
	
	/**
	 * 	
	 * @param from
	 * @param to
	 * @param state
	 * @return
	 * @throws SQLException If there's an issue connecting to the database.
	 */
	public List<OverallResult> fetchResultsWithDateState(String from, String to, String state)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithDateState(from, to, state);
		return list;
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param month
	 * @return
	 * @throws SQLException If there's an issue connecting to the database.
	 */
	public List<OverallResult> fetchResultsWithDateMonth(String from, String to, String month)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithDateMonth(from , to , month);
		return list;
	}
	
	/**
	 * 
	 * @param state
	 * @param month
	 * @return
	 * @throws SQLException If there's an issue connecting to the database.
	 */
	public List<OverallResult> fetchResultsWithStateMonth(String state,String month)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithStateMonth(state,month);
		return list;
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param month
	 * @param state
	 * @return
	 * @throws SQLException If there's an issue connecting to the database.
	 */
	public List<OverallResult> fetchResultsWithDateMonthState(String from, String to,String month,String state)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithDateMonthState(from, to, month, state);
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Deprecated
	public HashMap<String,BigInteger> fetchFilteredResult(){
		
		ResultDAO resultDAO = new ResultDAOImpl();
		HashMap<String, BigInteger> results = resultDAO.filteredSelect(filterForResult);
		return results;
		
	}
	
	@Deprecated
	public List<Map<String,Object>> fetchAllResultsForHail()throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<Map<String,Object>> hailList = resultDAO.fetchAllResultsForHail();
		return hailList;
	}
	
	@Deprecated
	public List<Map<String,Object>> fetchAllResultsForThunderStormWind()throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<Map<String,Object>> thunderstormWindList = resultDAO.fetchAllResultsForThunderStormWind();
		return thunderstormWindList;
		
	}
	
	@Deprecated
	public List<Map<String,Object>> fetchAllResultsForFlashflood()throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<Map<String,Object>> flashfloodList = resultDAO.fetchAllResultsForFlashflood();
		return flashfloodList;
		
	}
	
	@Deprecated
	public List<Map<String,Object>> fetchAllResultsForTornado()throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<Map<String,Object>> tornadoList = resultDAO.fetchAllResultsForTornado();
		return tornadoList;
		
	}
	
	@Deprecated
	public List<OverallResult> fetchResultsWithClassification(String morphology)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchAllResultsWithClassification(morphology);
		return list;
	}
	
	@Deprecated
	public List<OverallResult> fetchResultsWithClassificationAndState(String morphology, String state)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithClassificationAndState(morphology,state);
		return list;
	}
	
	
}
