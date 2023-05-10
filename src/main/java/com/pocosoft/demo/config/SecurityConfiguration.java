package com.pocosoft.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
	 @Autowired
	    private PortalWebAuthenticationDetailsSource authenticationDetailsSource;
	
	@Bean
	public SecurityFilterChain defaultSecurityConfigure(HttpSecurity http) throws Exception
	{
		http.csrf().disable().authorizeHttpRequests().antMatchers("/login", "/register/user").permitAll()
		.antMatchers("/service/welcome").authenticated()
		//.anyRequest().authenticated()
		.and()
		.formLogin().authenticationDetailsSource(authenticationDetailsSource).loginPage("/login").failureUrl("/login/failed?error")
		.and()
		.httpBasic();
		
		return http.build();
	}
	
	
	
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	
	/*
	@Bean
	public UserDetailsService userDetailsService(DataSource datasource)
	{
		return new JdbcUserDetailsManager(datasource);
	}
*/
}
