package com.pocosoft.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebserviceController {
	
	@GetMapping("/service/welcome")
	
	public String welcome()
	{
		return "HERE IS A WELCOME MESSAGE AFTER AUTHENTICATION";
	}

}
