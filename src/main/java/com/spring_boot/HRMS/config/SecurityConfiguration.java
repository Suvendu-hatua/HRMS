package com.spring_boot.HRMS.config;

import com.spring_boot.HRMS.exceptionHandling.CustomAccessDeniedHandler;
import com.spring_boot.HRMS.exceptionHandling.CustomBasicAuthenticationEntryPoint;
import com.spring_boot.HRMS.filter.CsrfTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class SecurityConfiguration {

    /**
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /**
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler=new CsrfTokenRequestAttributeHandler();

        http.securityContext(securityContext->securityContext.requireExplicitSave(false))
                //Session Management for Spring Security
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                . authorizeHttpRequests(requests-> requests
                        .requestMatchers("/hr/**").hasRole("HR")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/candidate/**").hasRole("CANDIDATE")
                        .requestMatchers("/jobs/**",
                                "/register",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        //CSRF Protection
        http.csrf(csrfConfig->csrfConfig
                .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                .ignoringRequestMatchers(
                        "/register")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
        //CSRF/XSRF token will be generated after Basic Authentication is complete.
        http.addFilterAfter(new CsrfTokenFilter(), BasicAuthenticationFilter.class);

        http.formLogin(Customizer.withDefaults());
        http.httpBasic(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }
}
