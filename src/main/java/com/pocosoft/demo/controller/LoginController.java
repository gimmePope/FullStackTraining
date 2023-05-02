package com.pocosoft.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pocosoft.demo.model.PortalUser;
import com.pocosoft.demo.repository.PortalUserRepository;

@Controller
public class LoginController {
	
	@Autowired
	PortalUserRepository userRepository;
	
	@GetMapping("/login")
	public String verifyUserCredentials()
	{
		return "main/index";
	}
	
	@PostMapping("/register/user")
	public ResponseEntity<String> registerUser(@RequestBody PortalUser user)
	{
		PortalUser savedUser = null;
		ResponseEntity response = null;
		try
		{
			savedUser = userRepository.save(user);
			if(savedUser.getId() > 0)
			{
				response = ResponseEntity.status(HttpStatus.CREATED).body("GIVEN USER DETAILS SUCCESSFUL REGISTERED");
			}
		}
		catch(Exception ex)
		{
			response.status(HttpStatus.INTERNAL_SERVER_ERROR).body("AN ERROR OCCURED TRYING TO REGISTER GIVEN USER DETAILS");
		}
		return response;
	}
	
	

}
