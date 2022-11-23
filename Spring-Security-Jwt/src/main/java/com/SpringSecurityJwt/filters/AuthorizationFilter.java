package com.SpringSecurityJwt.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.SpringSecurityJwt.Jwt.JwtUtil;
import com.SpringSecurityJwt.service.UserDetailService;

@Component
public class AuthorizationFilter extends OncePerRequestFilter
{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailService userDetailService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization");
		
		if(token != null)
		{
			String username = jwtUtil.getUsername(token);
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
			{
				UserDetails user = userDetailService.loadUserByUsername(username);
				
				boolean isValid = jwtUtil.validateToken(token, user.getUsername());
				
				if(isValid)
				{
					UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),user.getAuthorities());
				
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

	
}