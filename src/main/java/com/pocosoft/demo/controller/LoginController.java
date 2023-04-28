package com.pocosoft.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String verifyUserCredentials()
	{
		return "main/index";
	}
	
	

}
