package com.meteorology.swat.analytics;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meteorology.swat.service.ResultService;

@Controller
public class AnalyticsController {

	@RequestMapping(value = "/result/analytics" , method = RequestMethod.GET)
	public String analytics(HttpSession session, Model model) throws SQLException
	{
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
			if(userHasResultAccess)
			{
				AnalyticsService analyticsService = new AnalyticsService();
				List<AnalyticsReportBean> analyticsWithDateAndCount = analyticsService.getOverallAnalyticsReport();
				model.addAttribute("analyticsWithDateAndCount",analyticsWithDateAndCount);
				
				for(AnalyticsReportBean aRB: analyticsWithDateAndCount){
						System.out.println("Key:"+ aRB.getDateTime() + ", Value:" + aRB.getCount());
					
				}
				
				return "analytics";
				
			}
			else
			{
				model.addAttribute("error", "You do not have access to this page");
				return "error";
			}
			
		}
	}
	
	
	@RequestMapping(value = "/result/analytics-with-filter" , method = RequestMethod.GET)
	public String analyticsWithFilter(AnalyticsFilter analyticsFilter,HttpSession session, Model model) throws SQLException
	{
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
			if(userHasResultAccess)
			{
				AnalyticsService analyticsService = new AnalyticsService();
				List<Map<String,Object>> analyticsWithDateAndCount = analyticsService.getAnalyticsReport(analyticsFilter);
				model.addAttribute("analyticsWithDateAndCount",analyticsWithDateAndCount);
				return "analytics";
				
			}
			else
			{
				model.addAttribute("error", "You do not have access to this page");
				return "error";
			}
			
		}
	}
	
	
	@RequestMapping(value = "/result/analytics-classification" , method = RequestMethod.GET)
	public String analyticsClassfication(@RequestParam String dateTime,@RequestParam String region,HttpSession session, Model model) throws SQLException
	{
		
		System.out.println(dateTime);
		if(session.getAttribute("userEmail") == null 
				|| (
						session.getAttribute("userHasResultAccess") != null 
						&&  
						!(boolean)(session.getAttribute("userHasResultAccess") ))){
			model.addAttribute("error", "You do not have access to this page");
			return "error";
		}
		else
		{
			ResultService  rs = new ResultService();
			boolean userHasResultAccess = rs.userHasResultAccess((String)session.getAttribute("userEmail"));
			session.setAttribute("userHasResultAccess", userHasResultAccess);
			if(userHasResultAccess)
			{
				AnalyticsService analyticsService = new AnalyticsService();
				List<AnalyticsClassificationBean> analyticsWithClassificationAndUser 
					= analyticsService.getClassificationAndUserForGivenDate(dateTime,region);
				
				List<ExpertClassification> expertClassificationsList 
					= analyticsService.getExpertClassification(dateTime, region);
				model.addAttribute("analyticsWithClassificationAndUser",analyticsWithClassificationAndUser);
				model.addAttribute("expertClassificationsList",expertClassificationsList);
				model.addAttribute("dateTime", dateTime);
				model.addAttribute("region", region);
				for(AnalyticsClassificationBean aRB: analyticsWithClassificationAndUser){
					System.out.println(aRB.getClassification() + "," + aRB.getEmailAddress());
				
			}
			
				
				return "analytics-classification";
				
			}
			else
			{
				model.addAttribute("error", "You do not have access to this page");
				return "error";
			}
		}
	}
	
	@RequestMapping(value = "/result/analytics-classification" , method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody AnalyticsClassificationSubmitResponse analyticsClassficationSubmit
	(@RequestBody ExpertClassification expertClassification,HttpSession session) throws SQLException
	{
		System.out.println(expertClassification.getClassification());
		System.out.println(expertClassification.getDateTime());
		System.out.println(expertClassification.getRegion());
		if(session.getAttribute("userEmail") == null 
				|| (
						session.getAttribute("userHasResultAccess") != null 
						&&  
						!(boolean)(session.getAttribute("userHasResultAccess") ))){
			AnalyticsClassificationSubmitResponse analyticsClassificationSubmitResponse 
					= new AnalyticsClassificationSubmitResponse();
			analyticsClassificationSubmitResponse.setMessage("You do not have access to this page!");
			return analyticsClassificationSubmitResponse;
		}
		else
		{
			ResultService  rs = new ResultService();
			String userEmail = (String)session.getAttribute("userEmail");
			boolean userHasResultAccess = rs.userHasResultAccess(userEmail);
			if(userHasResultAccess)
			{
				AnalyticsService analyticsService = new AnalyticsService();
				expertClassification.setEmailAddress(userEmail);
				boolean inserted = analyticsService.insertExpertClassification(expertClassification);
				
				AnalyticsClassificationSubmitResponse analyticsClassificationSubmitResponse 
					= new AnalyticsClassificationSubmitResponse();
				if(inserted)
				{
					analyticsClassificationSubmitResponse.setExpertClassification(expertClassification);
					analyticsClassificationSubmitResponse.setMessage("Your classification has been recorded!");
					return analyticsClassificationSubmitResponse;
				}
				else
				{
					analyticsClassificationSubmitResponse.setMessage("Your classification has been recorded!");
					return analyticsClassificationSubmitResponse;
				}
				
			}
			else
			{
				AnalyticsClassificationSubmitResponse analyticsClassificationSubmitResponse 
				= new AnalyticsClassificationSubmitResponse();
				analyticsClassificationSubmitResponse
				.setMessage("You have gotten logged out due to inactivity. Login again!");
				return analyticsClassificationSubmitResponse;
			}
			
		}
	
		
	}
	
}
