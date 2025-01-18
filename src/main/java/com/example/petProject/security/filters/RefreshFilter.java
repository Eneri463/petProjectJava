package com.example.petProject.security.filters;

import com.example.petProject.security.tokensFactory.JwtFromRequest;
import com.example.petProject.security.tokensFactory.TokenGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RefreshFilter extends OncePerRequestFilter {

    @Autowired
    private TokenGenerator tokenGenerator;

    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/jwt/refresh", HttpMethod.POST.name());

    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
        if (this.requestMatcher.matches(request)) {

            if (this.securityContextRepository.containsContext(request)) {

                var context = this.securityContextRepository.loadDeferredContext(request).get();

                if (context != null && context.getAuthentication() instanceof UsernamePasswordAuthenticationToken &&
                        tokenGenerator.validateRefreshToken(JwtFromRequest.getJWTFromRequest(request))) {

                }
                    var accessToken = this.accessTokenFactory.apply(user.getToken());

                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    this.objectMapper.writeValue(response.getWriter(),
                            new Tokens(this.accessTokenStringSerializer.apply(accessToken),
                                    accessToken.expiresAt().toString(), null, null));
                    return;
                }
            }

            throw new AccessDeniedException("User must be authenticated with JWT");
            */


    }
}
