package com.UpperFasster.Magazine.config.security;

import com.UpperFasster.Magazine.authorization.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomAuthRoleChecker extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

//        String token = jwtService.getTokenFromBearer(
//                request.getHeader("Authorization")
//        );
//        DecodedJWT decodedJWT = jwtService.getPayloadFromToken(token);
//
//        UUID user_id = UUID.fromString(decodedJWT.getSubject());


        chain.doFilter(request, response);
    }
}
