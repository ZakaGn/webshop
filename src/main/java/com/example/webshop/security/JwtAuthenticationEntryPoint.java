package com.example.webshop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
		throws IOException
	{
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		Map<String, Object> data = new HashMap<>();
		data.put("timestamp", System.currentTimeMillis());
		data.put("status", HttpStatus.UNAUTHORIZED.value());
		data.put("message", "Access Denied: Authentication is required to access this resource.");
		data.put("path", request.getRequestURI());
		response.getOutputStream().println(new ObjectMapper().writeValueAsString(data));
	}
}
