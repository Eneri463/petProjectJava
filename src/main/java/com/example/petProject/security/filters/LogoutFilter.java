package com.example.petProject.security.filters;

import com.example.petProject.models.RefreshToken;
import com.example.petProject.models.UserEntity;
import com.example.petProject.security.tokensFactory.JwtFromRequest;
import com.example.petProject.services.RefreshTokenServiceInterface;
import com.example.petProject.services.impl.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LogoutFilter extends OncePerRequestFilter{

    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/jwt/logout", HttpMethod.POST.name());

    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    @Autowired
    RefreshTokenServiceInterface refreshTokenService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean withoutError = true;

        if (this.requestMatcher.matches(request)) {

            withoutError = false;

            if (this.securityContextRepository.containsContext(request)) {

                var context = this.securityContextRepository.loadDeferredContext(request).get();

                if (context != null && context.getAuthentication() instanceof UsernamePasswordAuthenticationToken &&
                        context.getAuthentication().getAuthorities()
                                .contains(new SimpleGrantedAuthority("JWT_LOGOUT"))) {

                    var auth = context.getAuthentication();
                    UserEntity user = customUserDetailsService.loadUser(auth.getName());

                    RefreshToken refreshToken = new RefreshToken(user.getId(), JwtFromRequest.getJWTFromRequest(request));

                    if (refreshTokenService.existsInDb(refreshToken))
                    {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                        var out = response.getWriter();
                        out.print("Logout was completed successfully");
                        out.flush();

                        refreshTokenService.delete(user.getId());

                        withoutError = true;
                    }
                }
            }
        }

        if (!withoutError)
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User must be authenticated with JWT");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
