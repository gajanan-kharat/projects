package com.auth.authgateway.auth;

import com.auth.authgateway.dto.UserDto;
import com.auth.authgateway.exception.TokenAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Component
@Slf4j
@Order(2)
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> requestTokenHeader = Optional.ofNullable(request.getHeader("Authorization"));
        Optional<UserDto> userDetails = null;
        String jwtToken = null;
        if(requestTokenHeader.isPresent() && requestTokenHeader.get().startsWith("Bearer")){
             jwtToken= getToken(requestTokenHeader);
             try {
                 userDetails = jwtUtil.getUserDetailsFromToken(jwtToken);
             }catch (IllegalArgumentException | ExpiredJwtException e){
                 System.out.println("Unable to get jwt token or jwt token has expired");

                 throw new JwtException("Unable to get jwt token or jwt token has expired");
             }
        }else{
            log.warn("JWT token does not begin 'Bearer' String");
        }
        log.info("User name is: {}", userDetails.get().getFirstName());
        SecurityContext context = SecurityContextHolder.getContext();

        if (userDetails.isPresent() && userDetails.get().getUserId()!=null){
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authenticationToken);

            // Persist the context
            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);
        }
        filterChain.doFilter(request,response);
    }

    private String getToken(Optional<String> token) {
        if(token.isPresent()) {
            String tokenValue = token.get().substring("Bearer".length()).trim();
            if(tokenValue.isEmpty()) {
                throw new TokenAuthenticationException(HttpStatus.BAD_REQUEST, "Invalid Token type");
            }
            return tokenValue;
        }
        return null;
    }
}
