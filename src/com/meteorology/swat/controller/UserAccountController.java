package com.meteorology.swat.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.meteorology.swat.bean.ProfileResult;
import com.meteorology.swat.service.UserProfileService;

/**
 * Controller class to control the user account related operations
 * @author Krishnan Subramanian
 *
 */
public class UserAccountController {
	@RequestMapping(value = "/profile/delete" , method = RequestMethod.GET)
	public ModelAndView profile(HttpSession session){
		ModelAndView model= null;
		if(session.getAttribute("userEmail")!= null)
		{
			model= new ModelAndView("frontpage");
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
}
