package com.pocosoft.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pocosoft.demo.config.PortalUserAuthenticationProvider;
import com.pocosoft.demo.model.LogInResponse;
import com.pocosoft.demo.model.PortalUser;
import com.pocosoft.demo.model.UserCredentials;
import com.pocosoft.demo.repository.PortalUserRepository;
import com.pocosoft.demo.util.OTPUtil;

@Controller
public class LoginController {
	
	@Autowired
	PortalUserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	PortalUserAuthenticationProvider authProvider;
	
	@GetMapping("/login")
	public String verifyUserCredentials()
	{
		return "main/index";
	}
	
	@PostMapping("/api/login")
	public ResponseEntity<LogInResponse> authenticationUser(@RequestBody UserCredentials credentials)
	{
		Object [] authResponse = authProvider.verifyUserCredentials(credentials);
		
		boolean isLoggedIn = (Boolean) authResponse[0];
		LogInResponse response = new LogInResponse(); 
		
		if(isLoggedIn)
		{
			PortalUser userInfo = (PortalUser) authResponse[2];
			String msg = (String) authResponse[1];
			response.setAuthenticated(isLoggedIn);
			response.setUsername(userInfo.getUsername());
			response.setResponseMessage(msg);	
		}
		else
		{
			String msg = (String) authResponse[1];
			response.setAuthenticated(isLoggedIn);
			response.setResponseMessage(msg);	
		}
			
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/register/user")
	public ResponseEntity<String> registerUser(@RequestBody PortalUser user)
	{
		PortalUser savedUser = null;
		ResponseEntity response = null;
		try
		{
			String hashedPwd = passwordEncoder.encode(user.getUserPassword());
			user.setUserPassword(hashedPwd);
			savedUser = userRepository.save(user);
			if(savedUser.getId() > 0)
			{
				response = ResponseEntity.status(HttpStatus.CREATED).body("GIVEN USER DETAILS SUCCESSFUL REGISTERED");
			}
		}
		catch(Exception ex)
		{
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("AN ERROR OCCURED TRYING TO REGISTER GIVEN USER DETAILS");
		}
		return response;
	}
	
	
	@GetMapping("generate/otp/{user}")
	public  ResponseEntity<OTPResponse> getOTP(@PathVariable("user") String userId)
	{
		PortalUser userInview = userRepository.findByUsername(userId);
		OTPResponse resp = new OTPResponse();
		if(userInview == null)
		{
			resp.setResponseCode("99");
			resp.setDescription("User not found");
			
			return new ResponseEntity<OTPResponse>(resp, HttpStatus.BAD_REQUEST);
		}
			 
		
		String otp = OTPUtil.generateOTP();
		String otpHash = OTPUtil.hash(otp, "SHA-512");
		userInview.setUserToken(otpHash);
		userInview.setTokenTime(LocalDateTime.now());
		userRepository.save(userInview);
		
		resp.setResponseCode("00");
		resp.setDescription("Successful");
		resp.setToken(otp);
		
		return  new ResponseEntity<OTPResponse>(resp, HttpStatus.OK);
		
		
	}
	
	

}
