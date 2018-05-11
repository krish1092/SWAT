package com.meteorology.swat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.meteorology.swat.bean.ChangePasswordForm;
import com.meteorology.swat.bean.ForgotPasswordForm;
import com.meteorology.swat.bean.LoginForm;
import com.meteorology.swat.service.UserService;

@Controller
public class LoginController {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LoginController.class);
	
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("login");
		return model;
	}
	
	@RequestMapping(value = "/logout")
	public String logout(WebRequest request, SessionStatus status){
		
		status.setComplete();
		request.removeAttribute("loggedInUser", WebRequest.SCOPE_SESSION);
		return "redirect:/";
	}
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("loginForm")  LoginForm loginForm,HttpSession session)
	{
		ModelAndView model = null;
		userService = new UserService();
		
		try{
			boolean loginCredentialsCorrect = userService.login(loginForm);
			
			if(loginCredentialsCorrect)
			{
				logger.info("User "+loginForm.getEmailID()+" Successfully logged in!");
				request.setAttribute("signedInUser", loginForm.getEmailID());
				session.setAttribute("loggedInUser", userService.getName());
				session.setAttribute("userEmail", loginForm.getEmailID());
				model = new ModelAndView("navigationbar");
			}
			else{
				model = new ModelAndView("navigationbar");
				model.addObject("loginBean", new LoginForm());
				model.addObject("loginError", "The email address or password is not correct!");
				
			}
		}catch(Exception e){
			e.printStackTrace();
			model = new ModelAndView("error");
		}
		return model;
	}
	
	
	/*@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("loginForm")  LoginForm loginForm,HttpSession session)
	{
		ModelAndView model = null;
		userService = new UserService();
		
		try{
			boolean loginCredentialsCorrect = userService.login(loginForm);
			
			if(loginCredentialsCorrect)
			{
				logger.info("User "+loginForm.getEmailID()+" Successfully logged in!");
				request.setAttribute("signedInUser", loginForm.getEmailID());
				session.setAttribute("loggedInUser", userService.getName());
				model = new ModelAndView("signedin");
			}
			else{
				model = new ModelAndView("login");
				model.addObject("loginBean", new LoginForm());
				request.setAttribute("error", "Error in Credentials");
			}
		}catch(Exception e){
			e.printStackTrace();
			model = new ModelAndView("error");
		}
		return model;
	}
	*/
	
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String forgotPassword(HttpServletRequest request, HttpServletResponse response,
			HttpSession session,Model model){
		if((String) session.getAttribute(("signedInUser")) == null)
		{
			return "forgotpassword";
		}
		else
		{
			model.addAttribute("error","You are already logged in! Please click on change password option!");
			return "error";
		}
		
		
	}
	
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	@ResponseBody
	public String resendPassword(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("forgotPasswordForm")  ForgotPasswordForm forgotPasswordForm, HttpSession session){
		
		
		if((String) session.getAttribute(("signedInUser")) == null)
		{
			UserService userService = new UserService();
			logger.info("forgotPasswordForm.getEmailId : "+forgotPasswordForm.getEmailID());
			userService.setUrl(request,"changePassword");
			userService.forgotPassword(forgotPasswordForm.getEmailID());
			return "If your account exists, an email will be sent with the reset password instructions";
		}
		else
		{
			return "If your account exists, an email will be sent with the reset password instructions";
		}
		
		
	}
	
	
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET )
	public String changePasswordFormDisplay(Model model, @RequestParam("token") String token){
		if(token.equalsIgnoreCase("") || token == null){
			model.addAttribute("error", "Give a unique token");
			return "error";
		}
		else
		{
			ChangePasswordForm changePasswordForm = new ChangePasswordForm();
			logger.info("token:"+token);
			changePasswordForm.setToken(token);
			model.addAttribute("changePasswordForm",changePasswordForm);
			return "changepassword";
		}
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST )
	public @ResponseBody String changePassword(Model model, 
			@ModelAttribute("changePasswordForm")  ChangePasswordForm changePasswordForm)
	{
		logger.info("changePasswordForm.getToken():"+changePasswordForm.getToken());
		UserService userService = new UserService();
		if(changePasswordForm.getPassword().equals(changePasswordForm.getReenterPassword()))
		{
			userService.changePassword(changePasswordForm.getToken(),changePasswordForm.getPassword());
			return "The password has been changed!";
		}
		else
		{
			//model.addAttribute("error", "The passwords do not match");
			return "The passwords do not match";
		}
		
	}
		
	
}
