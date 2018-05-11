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

public class ResultService {

	private FilterForResult filterForResult;
	public ResultService(FilterForResult filterForResult){
		this.filterForResult = filterForResult;
	}
	public ResultService(){
		
	}
	
	
	
	public boolean userHasResultAccess(String userEmail) throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		
		boolean userHasAccess = resultDAO.userHasResultAccess(userEmail);
		return userHasAccess;
		
	}
	
	public List<OverallResult> getOverallResult() throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchAllResults();
		return list;
	}
	
	public List<OverallResult> fetchResultsWithState(String state)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithState(state);
		return list;
	}
	
	public List<OverallResult> fetchResultsWithDates(String from, String to)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithDates(from, to);
		return list;
	}
	
	public List<OverallResult> fetchResultsWithMonth(String month)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithMonth(month);
		return list;
	}
	
	
	
	public List<OverallResult> fetchResultsWithDateState(String from, String to, String state)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithDateState(from, to, state);
		return list;
	}
	

	public List<OverallResult> fetchResultsWithDateMonth(String from, String to, String month)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithDateMonth(from , to , month);
		return list;
	}
	
	
	public List<OverallResult> fetchResultsWithStateMonth(String state,String month)throws SQLException{
		ResultDAO resultDAO = new ResultDAOImpl();
		resultDAO.setDataSource();
		List<OverallResult> list = resultDAO.fetchResultsWithStateMonth(state,month);
		return list;
	}
	
	
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
