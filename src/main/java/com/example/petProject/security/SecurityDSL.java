package com.example.petProject.security;

import com.example.petProject.security.filters.AuthFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Component;

@Component
public class SecurityDSL extends AbstractHttpConfigurer<SecurityDSL, HttpSecurity> {

    private AuthFilter authFilter;

    //todo
    @Override
    public void init(HttpSecurity http) throws Exception {
        var csrfConfigurer = http.getConfigurer(CsrfConfigurer.class);
        if (csrfConfigurer != null) {
            csrfConfigurer.ignoringRequestMatchers("/auth/**");
        }
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(authFilter, CsrfFilter.class);
    }

    public SecurityDSL authFilter(AuthFilter authFilter) {
        this.authFilter = authFilter;
        return this;
    }

    public static SecurityDSL createDsl() {
        return new SecurityDSL();
    }

}
