package com.pocosoft.demo.config;



import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.pocosoft.demo.model.PortalUser;
import com.pocosoft.demo.repository.PortalUserRepository;
import com.pocosoft.demo.util.OTPUtil;


@Component
public class PortalUserAuthenticationProvider implements AuthenticationProvider  {
	
	@Autowired
	private PortalUserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		 	String username = authentication.getName();
	        String password = authentication.getCredentials().toString();
	        String otp = ((PortalWebAuthenticationDetails) authentication.getDetails()).getOneTimePassword();
	        System.out.println("User: " + username + ", Password: " + password + ", OTP: " + otp);
	        PortalUser userDetails = userRepository.findByUsername(username);
	        if(userDetails == null)
	        	 throw new BadCredentialsException("Authentication failed");
	        if (passwordMatches(password, userDetails.getUserPassword()) && otpMatches(otp, userDetails.getUserToken(), userDetails.getTokenTime())) {
	        	List<GrantedAuthority> roles = new ArrayList<>();
	        	roles.add(new SimpleGrantedAuthority(userDetails.getUserRole()));
	            return new UsernamePasswordAuthenticationToken(username, password, roles);
	        }
	        else
	        {
	            throw new BadCredentialsException("Authentication failed");
	        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
	private boolean otpMatches(String otp, String storedOtp, LocalDateTime tokeTime) {
        // Implement OTP comparison logic
		//boolean tokenExpired = false;
		
		LocalDateTime currTime = LocalDateTime.now();
		
	    Duration duration = Duration.between(tokeTime, currTime);
		long seconds = duration.toSeconds();
		System.out.println("Seconds since generated : " + seconds);
		System.out.println("Min since generation: " + duration.toMinutes() );
		if(seconds > (60*5))
		{
			System.out.println("TOKEN EXPIRED!!!!");
			return false;
		}
		String otpHash = OTPUtil.hash(otp, "SHA-512");
		return otpHash.equals(storedOtp);
    }

	 private boolean passwordMatches(String rawPassword, String encodedPassword) {
	        // Implement password comparison logic (e.g., using BCryptPasswordEncoder)
		 return passwordEncoder.matches(rawPassword, encodedPassword);
	    }

}
