package com.sms.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.sms.persistence.domain.Administrator;

@Controller
public class AuthenticationController {
	
	
	@RequestMapping(value= "/login", method= RequestMethod.GET)
	public ModelAndView loginPge(
			@RequestParam(value = "error",  required = false) String error, 
			@RequestParam(value = "logout", required = false) String logout){
		
		   ModelAndView model = new ModelAndView("login");
		   if(null != error){
			   model.addObject("error", "Invalid UserName or Password!");
		   }
		   if(null != logout){
			   model.addObject("logout", "You have been logged out successfully");
		   }
		   return model;
	}
	
	@RequestMapping(value= {"/","/welcome**"}, method= RequestMethod.GET)
	public ModelAndView welcomePage(HttpServletRequest req){
		   ModelAndView model = new ModelAndView("welcome");
		   HttpSession session = req.getSession();
		   session.setAttribute("firstName", getFirstName());
		   return model;
	}
	
	@RequestMapping(value= "/logout", method= RequestMethod.GET)
	public String logoutPage(HttpServletRequest req, HttpServletResponse res){
		   Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		   if(auth != null){
			    new SecurityContextLogoutHandler().logout(req, res, auth);
		   }
		        return "redirect:/login?logout";
	}


	private String getFirstName() {
		    String name = null;
		    Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    if(obj instanceof Administrator)
		       name = ((Administrator) obj).getFirstName();
		       return name;
	}

 }
