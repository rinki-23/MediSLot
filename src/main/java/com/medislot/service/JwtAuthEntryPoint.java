package com.medislot.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;



@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		response.setStatus(401);
		response.setContentType("application/json");
		Map<String, Object> body = Map.of(
				"status" , 401,
				"error" , "Unauthorized",
				"message" , "Login Required. Token is missing, invalid or expired.",
				"path" , request.getRequestURI()
			);
		
		//Object Mapper convert the object into json text and write it into response
		new ObjectMapper().writeValue(response.getWriter(), body);
		
		
	}
	
}
