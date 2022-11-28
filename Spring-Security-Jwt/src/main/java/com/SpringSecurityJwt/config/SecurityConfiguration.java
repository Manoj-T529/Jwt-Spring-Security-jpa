package com.SpringSecurityJwt.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.SpringSecurityJwt.filters.AuthorizationFilter;



@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

	
	@Autowired
	private InvalidAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private AuthorizationFilter authorizationFilter;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
	{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http.csrf().disable();
		
		http.authorizeRequests()
			.antMatchers("/api/user/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
//	@Bean
//	SecurityWebFilterChain http(ServerHttpSecurity http) throws Exception {
//	    DelegatingServerLogoutHandler logoutHandler = new DelegatingServerLogoutHandler(
//	            new WebSessionServerLogoutHandler(), new SecurityContextServerLogoutHandler()
//	    );
//
//	    http
//	        .authorizeExchange((exchange) -> exchange.anyExchange().authenticated())
//	        .logout((logout) -> logout.logoutHandler(logoutHandler));
//
//	    return http.build();
//	}
}
