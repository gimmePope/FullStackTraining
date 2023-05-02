package com.pocosoft.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pocosoft.demo.model.PortalUser;
import com.pocosoft.demo.repository.PortalUserRepository;

@Service
public class PortalUserDetailsService implements UserDetailsService {
	
	@Autowired
	PortalUserRepository portalUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String user, password = null;
		List <GrantedAuthority> authorities = null;
		
		PortalUser portalUser = portalUserRepository.findByEmail(username);
		
		if(portalUser == null)
			throw new UsernameNotFoundException("The email " + username + " is not attached to any user.");
		else
		{
			user = portalUser.getEmail();
			password = portalUser.getUserPassword();
			authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(portalUser.getUserRole()));
		}
		
		return new User(user, password, authorities);
	}

}
