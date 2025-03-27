package com.nipa.agroneed.config;

import com.nipa.agroneed.entity.User;
import com.nipa.agroneed.repository.UserRepository;
import com.nipa.agroneed.service.Impl.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthAndCookieFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(JwtAuthAndCookieFilter.class);
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthAndCookieFilter(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            // Retrieve JWT token from the request header
            String jwt = getJwtFromRequest(httpServletRequest);

            // If JWT token is not present in the header, check the cookies
            if (!StringUtils.hasText(jwt)) {
                Cookie[] cookies = httpServletRequest.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (SecurityConfig.COOKIES_JWT_TOKEN_KEY.equals(cookie.getName())) {
                            // Add the JWT token to the request header
                            httpServletRequest = new HttpServletRequestWrapper(httpServletRequest) {
                                @Override
                                public String getHeader(String name) {
                                    if ("Authorization".equals(name)) {
                                        return "Bearer " + cookie.getValue();
                                    }
                                    return super.getHeader(name);
                                }
                            };
                            break;
                        }
                    }
                }
            }

            // Validate the JWT token
            if (StringUtils.hasText(jwt) && jwtTokenProvider.isValidateToken(jwt, httpServletRequest)) {
                Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
                User user = userRepository.findByIdAndStatus(userId, 1);

                if (user != null) {
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getPhone());
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Set the authentication in the security context
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            // Log the exception
            logger.error("Something went wrong during handle COOKIES_JWT_TOKEN_KEY or prepare user details :{}", e.getMessage(), e);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);//after jwt authenticate user can be permited for API.


    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); //get authorizaytio value from http request header
        //String ip = request.getRemoteAddr();
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) { //token hasEmprty or not and Is token start with bearer token or not?
            return bearerToken.substring(7); //if valid substring 7 use kore first 7 character remove kora hoy.thn return jwt token
        }
        return null;  // if authorization header not valid then return null.
    }
}
