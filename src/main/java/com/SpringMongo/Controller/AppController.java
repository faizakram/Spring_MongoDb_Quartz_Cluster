package com.SpringMongo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/")
public class AppController {

	

	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser() {
		return "registration";
	}



}