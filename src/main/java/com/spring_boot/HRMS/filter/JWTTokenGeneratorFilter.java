package com.spring_boot.HRMS.filter;

import com.spring_boot.HRMS.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            Environment environment=getEnvironment();
            if(environment!=null){
                String secret=environment.getProperty(ApplicationConstants.JWT_SECRET_KEY,ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                String jwt=Jwts.builder()
                        .setIssuer("Human Resource Management System").setSubject("Jwt Token")
                        .claim("username",authentication.getName())
                        .claim("authorities",authentication.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority
                        ).collect(Collectors.joining(",")))
                        .setIssuedAt(new Date())
                        .setExpiration(new Date((new Date()).getTime()+3000000))
                        .signWith(secretKey).compact();
                response.setHeader(ApplicationConstants.JWT_HEADER,jwt);
            }
        }
        filterChain.doFilter(request,response);
    }

    /**
     * Can be overridden in subclasses for custom filtering control,
     * returning {@code true} to avoid filtering of the given request.
     * <p>The default implementation always returns {@code false}.
     *
     * @param request current HTTP request
     * @return whether the given request should <i>not</i> be filtered
     * @throws ServletException in case of errors
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //JWT token will be only generated when these apis will be invoked---> /hrms/admin, /hrms/candidate, /hrms/hr
        String path=request.getServletPath();
        log.info("Servlet Path:",path);
        return !path.equals("/admin") && !path.equals("/hr") && !path.equals("/candidate");
    }
}
