package com.spring_boot.HRMS.exceptionHandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

            String message = (authException != null && authException.getMessage() != null) ? authException.getMessage()
                : "Unauthorized";
            String path = request.getRequestURI();
            //Setting headers and content type
            response.setHeader("hrms-error-reason","Authentication Failed");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");

            //Construct JSON Response
        String jsonResponse=String.format("{\"timeStamp\":\"%s\",\"status\":\"%d\",\"error\":\"%s\",\"message\":\"%s\",\"path\":\"%s\"}",
                System.currentTimeMillis(),HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                message,path
                );
        response.getWriter().write(jsonResponse);
    }
}
