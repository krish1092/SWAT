package com.meteorology.swat.controller;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
/*import javax.validation.Valid;*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.meteorology.swat.bean.SignUpForm;
import com.meteorology.swat.service.UserService;

/**
 * A controller for signup related operations.
 * @author Krishnan Subramanian
 *
 */
@Controller
public class SignUpFormController {

	private static final Logger logger = LoggerFactory.getLogger(SignUpFormController.class);

	/**
	 * Sign the user up to the application.
	 * @param signupForm The {@link SignUpForm} 
	 * @param result
	 * @param request
	 * @return A model
	 * @throws SQLException
	 */
	@RequestMapping(value = "/signup", method=RequestMethod.POST)
	public ModelAndView signup(/*@Valid*/ SignUpForm signupForm,BindingResult result
			,HttpServletRequest request) {
		if(result.hasErrors()) {
			ModelAndView model = new ModelAndView("signup");
			return model;
		} else {
			try {
				UserService userService = new UserService();
				userService.setUrl(request,"activate");
				userService.signup(signupForm);
			} catch (DataAccessException e) {
				logger.error(e.getMessage());
				ModelAndView model = new ModelAndView("error");
				model.addObject("error", 
						"There was an error communicating with the database. Please try again later.");
				return model;
				
			} catch (MessagingException e) {
				logger.error(e.getMessage());
				ModelAndView model = new ModelAndView("error");
				model.addObject("error", 
						"There was an error sending an email. Please check your email.");
				return model;
			}
			logger.info("Signup Details:");
			logger.info("Email Address:"+signupForm.getEmailID());
			logger.info("Name:"+signupForm.getName());
			ModelAndView model = new ModelAndView("signedup");
			return model;
		}
	}

	/**
	 * Activate the user account with the given token.
	 * @param token The token from the user (must not be null, empty, or whitespace).
	 * @return A model for authenticated.
	 */
	@RequestMapping(value = "/activate",  method = RequestMethod.GET)
	public ModelAndView activateAccount(@RequestParam String token) {
		
		if(null == token || token.trim().isEmpty()) {
			ModelAndView model = new ModelAndView("error");
			model.addObject("error", "The given token is invalid.");
			return model;
		}
		logger.info("Token:" + token);
		try {
			UserService userService = new UserService();
			boolean authenticated = userService.activateToken(token);
			ModelAndView model = new ModelAndView("activate");
			model.addObject("authenticated", authenticated);
			return model;
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			ModelAndView model = new ModelAndView("error");
			model.addObject("error", 
					"There was an error communicating with the database. Please try again later.");
			return model;
		}
	}
}
