package com.sistema_buses.config;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpSession;

public class FeignInterceptor implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        if (attributes == null) return;
        HttpSession session = attributes.getRequest().getSession(false);
        
        if (session == null) return;
        String token = (String) session.getAttribute("token");
        String correlationId = (String) session.getAttribute("X-Correlation-id");
        if (token != null) {
            template.header("Authorization", "Bearer " + token);
            
        }
        if(correlationId != null) {
        	template.header("X-Correlation-Id", correlationId);
        }
	}
}
