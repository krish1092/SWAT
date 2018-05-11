package com.meteorology.swat.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meteorology.swat.bean.OverallResult;
import com.meteorology.swat.model.FilterForResult;
import com.meteorology.swat.service.ResultService;

@Controller
public class ResultController {

	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public String showResults(HttpSession session,HttpServletResponse response,Locale locale, Model model) throws SQLException, IOException {
		if(session.getAttribute("userEmail") == null
				|| (session.getAttribute("userHasResultAccess") != null &&  !(boolean)(session.getAttribute("userHasResultAccess") ))){
			model.addAttribute("error", "You do not have access to this page");
			return "error";
		}
		else
		{
			ResultService  rs = new ResultService();
			boolean userHasResultAccess = rs.userHasResultAccess((String)session.getAttribute("userEmail"));
			session.setAttribute("userHasResultAccess", userHasResultAccess);
			boolean noNullEvents = false;
			if(userHasResultAccess)
			{
				
				ResultService resultService = new ResultService();
				model.addAttribute("overallResult",resultService.getOverallResult());
				model.addAttribute("noNullEvents", noNullEvents);
				return "result";
				
			}
			else
			{
				model.addAttribute("error", "You do not have access to this page");
				return "error";
			}
			
		}
	
		
	}
	
	
	@RequestMapping(value = "/resetResult", method = RequestMethod.POST)
	public String resetResult(Model model) throws SQLException {
		ResultService resultService = new ResultService();
		model.addAttribute("overallResult",resultService.getOverallResult());
		return "filteredresult";
	}
	
	
	
	@RequestMapping(value = "/filteredResult", method = RequestMethod.POST)
	public String showFilteredResults(FilterForResult filterForResult,HttpSession session,HttpServletResponse response,Locale locale, Model model) throws SQLException, IOException,ParseException {
		
		ResultService resultService = new ResultService(filterForResult);
		List<OverallResult> overallResult = null;
		boolean noNullEvents = false;
		
		if((filterForResult.getFromDate() == null && filterForResult.getToDate() == null ) || (filterForResult.getFromDate().equals("") && filterForResult.getToDate().equals("")))
		{
			if (filterForResult.getState() != null && filterForResult.getMonth() == null) 
			{
				if(filterForResult.getState().equalsIgnoreCase("ALL"))
				{
					overallResult = resultService.getOverallResult();
				}
				else
				{
					overallResult = resultService.fetchResultsWithState(filterForResult.getState());
					noNullEvents = true;
				}
			}
			else if (filterForResult.getState() == null && filterForResult.getMonth() != null ) 
			{
				overallResult = resultService.fetchResultsWithMonth(filterForResult.getMonth());
			}
			else if (filterForResult.getState() != null && filterForResult.getMonth() != null)
			{
				if(filterForResult.getState().equalsIgnoreCase("ALL"))
				{
					overallResult = resultService.fetchResultsWithMonth(filterForResult.getMonth());
				}
				else
				{
					overallResult = resultService.fetchResultsWithStateMonth(filterForResult.getState(), filterForResult.getMonth());
					noNullEvents = true;
				}
			}	
			else 
			{
				overallResult = resultService.getOverallResult();
			}
			
		}
		else
		{
			if (filterForResult.getState() == null && filterForResult.getMonth() == null ) 
			{
				overallResult = resultService.fetchResultsWithDates(date(filterForResult.getFromDate()), date(filterForResult.getToDate()));
			}
			else if (filterForResult.getState() != null && filterForResult.getMonth() == null ) 
			{
				if(filterForResult.getState().equalsIgnoreCase("ALL"))
				{
					overallResult = resultService.fetchResultsWithDates(date(filterForResult.getFromDate()), date(filterForResult.getToDate()));
				}
				else
				{
					overallResult = resultService.fetchResultsWithDateState(date(filterForResult.getFromDate()), date(filterForResult.getToDate()),filterForResult.getState());
					noNullEvents = true;
				}
			}
			else if (filterForResult.getState() == null && filterForResult.getMonth() != null ) 
			{
				overallResult = resultService.fetchResultsWithDateMonth(date(filterForResult.getFromDate()), date(filterForResult.getToDate()),filterForResult.getMonth());
			}
			else if (filterForResult.getState() != null && filterForResult.getMonth() != null)
			{
				if(filterForResult.getState().equalsIgnoreCase("ALL"))
				{
					overallResult = resultService.fetchResultsWithDateMonth(date(filterForResult.getFromDate()), date(filterForResult.getToDate()), filterForResult.getMonth());
				}
				else
				{
					overallResult = resultService.fetchResultsWithDateMonthState(date(filterForResult.getFromDate()), date(filterForResult.getToDate()), 
							filterForResult.getMonth(), filterForResult.getState());
					noNullEvents = true;
				}
			}
		}
		
		model.addAttribute("overallResult", overallResult);
		model.addAttribute("noNullEvents", noNullEvents);
		return "filteredresult";
	}
	
	public String date(String s) throws ParseException
	{
		DateTimeZone.setDefault(DateTimeZone.UTC);
		DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy");
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt = DateTime.parse(s , dtf);
		return dtfOut.print(dt);
	}

	
}
