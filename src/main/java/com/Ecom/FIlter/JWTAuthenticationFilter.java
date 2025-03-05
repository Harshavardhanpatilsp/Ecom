package com.Ecom.FIlter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Ecom.service.JWTservice;
import com.Ecom.service.UserService;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@EnableJpaRepositories
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	private final JWTservice jwtservice;
	private final UserService userservice;
	
	public JWTAuthenticationFilter(JWTservice jwtservice, UserService userservice) {
		this.jwtservice = jwtservice;
		this.userservice = userservice;
	}

	@Override
	protected void doFilterInternal( @NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		
		String authheader = request.getHeader("Authorization");
		
		if(authheader == null ||!authheader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		// TODO Auto-generated method stub
		String token = authheader.substring(7);
		String username = jwtservice.extractUsername(token);
		
		if(username != null & SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetailss = userservice.loadUserByUsername(username);
			
			if(jwtservice.isvalid(token, userDetailss)) {
				UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(userDetailss,null, userDetailss.getAuthorities());
				
				authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authtoken);
			}
			
			
		}

		filterChain.doFilter(request, response);
}
}
