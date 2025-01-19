package com.example.petProject.security.filters;

import com.example.petProject.security.tokensFactory.JwtFromRequest;
import com.example.petProject.security.tokensFactory.TokenGenerator;
import com.example.petProject.services.impl.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.*;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = JwtFromRequest.getJWTFromRequest(request);

        if(StringUtils.hasText(token)) {

            if (tokenGenerator.validateAccessToken(token) || tokenGenerator.validateRefreshToken(token))
            {
                Claims claims = tokenGenerator.getClaimsFromToken(token);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());

                List<GrantedAuthority> listAuthorities = new ArrayList<>();
                listAuthorities.addAll(userDetails.getAuthorities());
                listAuthorities.addAll(tokenGenerator.authorityFromClaims(claims));

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        Collections.unmodifiableList(listAuthorities));

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);

                securityContextRepository.saveContext(context, request, response);

                CsrfFilter.skipRequest(request);
            }
            else {
                throw new AccessDeniedException("User must be authenticated with JWT");
            }

        }

        filterChain.doFilter(request, response);
    }

}
