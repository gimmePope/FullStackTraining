package com.pocosoft.demo.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain defaultSecurityConfigure(HttpSecurity http) throws Exception
	{
		http.authorizeHttpRequests().antMatchers("/login").permitAll()
		.antMatchers("/service/welcome").authenticated()
		//.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").failureUrl("/login/failed?error")
		.and()
		.httpBasic();
		
		return http.build();
	}
	
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
	
	
	
	@Bean
	public UserDetailsService userDetailsService(DataSource datasource)
	{
		return new JdbcUserDetailsManager(datasource);
	}

}
