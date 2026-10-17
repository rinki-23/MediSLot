package com.medislot.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		response.setStatus(403);
		response.setContentType("application/json");
		Map<String, Object> json = Map.of(
				"status" , 403,
				"error" , "Forbidden",
				"message" , "You do not have permission to access this resource",
				"path" , request.getRequestURI()
				);
		
		new ObjectMapper().writeValue(response.getWriter(), json);
		
		
	}

	
	
}
