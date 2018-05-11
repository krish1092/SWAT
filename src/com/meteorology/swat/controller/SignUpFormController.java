package com.meteorology.swat.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*import javax.validation.Valid;*/


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.meteorology.swat.bean.SignUpForm;
import com.meteorology.swat.service.UserService;


@Controller
public class SignUpFormController {

	private static final Logger logger = LoggerFactory.getLogger(SignUpFormController.class);
	
	
	@RequestMapping(value = "/signup",  method = RequestMethod.GET)
	public ModelAndView displaySignUpForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("signup");
		logger.info(request.getLocalAddr());
		logger.info(request.getLocalName());
		SignUpForm signup = new SignUpForm();
		model.addObject(signup);
		return model;
	}
	

	@RequestMapping(value = "/signup", method=RequestMethod.POST)
	public ModelAndView signup(/*@Valid*/ SignUpForm signupForm,BindingResult result
			,HttpSession session, HttpServletRequest request) throws SQLException
	{
		
		ModelAndView model ;
		if(result.hasErrors()) 
		{
			model = new ModelAndView("signup");
			/*String message = "There is an error with your signup! Please check the details entered!";
			model.addObject(message);*/
			return model;
		}
		else{
		
			UserService userService = new UserService();
			userService.setUrl(request,"activate");
			userService.signup(signupForm);
			logger.info("Email ID:"+signupForm.getEmailID()+ ",Name:"+signupForm.getName());
			model = new ModelAndView("signedup");
			return model;
		}
		
	}
	
	
	@RequestMapping(value = "/activate",  method = RequestMethod.GET)
	public ModelAndView activateAccount(@RequestParam String token,HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ModelAndView model = new ModelAndView("activate");
		UserService userService = new UserService();
		boolean authenticated = userService.authenticate(token);
		model.addObject("authenticated", authenticated);
		return model;
	}
	
	
	
}
