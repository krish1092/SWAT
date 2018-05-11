package com.meteorology.swat.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meteorology.swat.bean.ProfileResult;
import com.meteorology.swat.model.ProfileSpecificObservationEventsCount;
import com.meteorology.swat.service.UserProfileService;
@Controller
public class ProfileController {
	
	@RequestMapping(value = "/profile" , method = RequestMethod.GET)
	public ModelAndView profile(HttpSession session){
		ModelAndView model= null;
		if(session.getAttribute("userEmail")!= null)
		{
			model= new ModelAndView("profile");
			UserProfileService userProfileService = new UserProfileService();
			Map<Long, List<ProfileResult>> mapWithList = 
					userProfileService.selectRecent((String)session.getAttribute("userEmail"));
			
			for(List<ProfileResult> list : mapWithList.values()){
				
				for(ProfileResult p : list){
					System.out.println(p.getStartTime() +" "+ p.getEndTime() +" "+ p.getClassification()
					+" "+ p.getHailCount());
				}
				
			}
			
			
			model.addObject("recentClassificationsMap",mapWithList);
		}
		else
		{
			model= new ModelAndView("profile");
			model.addObject("error", "Sign in to check your profile");
			
		}
		return model;
	}
	
	
	@RequestMapping(value = "/profile/specific-observation" , method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ProfileSpecificObservationEventsCount 
		getEventCountForSpecificObservation(@RequestParam long infoID, HttpSession session){
		
		UserProfileService userProfileService = new UserProfileService();
		
		ProfileSpecificObservationEventsCount counts = new ProfileSpecificObservationEventsCount();
		
		counts.setFlashfloodCount(userProfileService.getFlashfloodCounts(infoID));
		counts.setHailCount(userProfileService.getHailCounts(infoID));
		counts.setThunderstormCount(userProfileService.getThunderstormCounts(infoID));
		counts.setTornadoCount(userProfileService.getTornadoCounts(infoID));
		
		return counts;
		
	}

}
