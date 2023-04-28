package com.pocosoft.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
	public InMemoryUserDetailsManager userDetailsService()
	{
		UserDetails admin = User.withUsername("admin").password("pass123").authorities("admin").build();
		UserDetails user = User.withUsername("user").password("pass123").authorities("read").build();
		
		return new InMemoryUserDetailsManager(admin, user);
	}

}
