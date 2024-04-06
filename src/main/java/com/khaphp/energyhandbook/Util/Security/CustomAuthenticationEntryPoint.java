package com.khaphp.energyhandbook.Util.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ResponseObject responseObject = ResponseObject.builder()
                .code(401)
                .message("(Unauthorized) You must LOGIN.")
                .build();
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseObject));
    }
}
