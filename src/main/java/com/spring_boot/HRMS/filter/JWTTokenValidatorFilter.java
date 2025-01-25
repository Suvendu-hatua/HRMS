package com.spring_boot.HRMS.filter;

import com.spring_boot.HRMS.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JWTTokenValidatorFilter extends OncePerRequestFilter {
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

        String jwt=request.getHeader(ApplicationConstants.JWT_HEADER);
        if(jwt!=null){
          try{
              Environment environment=getEnvironment();
              String secret=environment.getProperty(ApplicationConstants.JWT_SECRET_KEY,ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
              SecretKey secretKey= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
              if(secretKey!=null){
                  Claims claims= Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
                  //Extracting username and authorities from payload data.
                  String username=(String) claims.get("username");
                  String authorities=(String) claims.get("authorities");
                  Authentication authentication=new UsernamePasswordAuthenticationToken(username,null,
                          AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                  //Adding authentication instance into SecurityContextHolder
                  SecurityContextHolder.getContext().setAuthentication(authentication);
              }
          }catch (Exception exception){
              throw new BadCredentialsException("Invalid JWT Token");
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
        String path=request.getServletPath();
        log.info("Servlet Path:",path);

        return path.equals("/admin") || path.equals("/hr") ||  path.equals("/candidate");
    }
}
