package com.example.petProject.security.filters;

import com.example.petProject.security.tokensFactory.JwtFromRequest;
import com.example.petProject.security.tokensFactory.TokenGenerator;
import com.example.petProject.services.impl.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = JwtFromRequest.getJWTFromRequest(request);

        if(StringUtils.hasText(token)) {

            if (tokenGenerator.validateAccessToken(token) || tokenGenerator.validateRefreshToken(token))
            {
                String username = tokenGenerator.getUsernameFromAccessToken(token);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                CsrfFilter.skipRequest(request);
            }
            else {
                throw new AccessDeniedException("User must be authenticated with JWT");
            }

        }

        filterChain.doFilter(request, response);
    }

}
