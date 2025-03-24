package com.nipa.agroneed.config;

import com.nipa.agroneed.entity.User;
import com.nipa.agroneed.repository.UserRepository;
import com.nipa.agroneed.service.Impl.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = getJwtFromRequest(httpServletRequest); //get jwt token from http request header
            if (StringUtils.hasText(jwt) && jwtTokenProvider.isValidateToken(jwt,httpServletRequest)) { //first check jwt token isEmpty or not, second jwt token validate or not
                Long userId = jwtTokenProvider.getUserIdFromToken(jwt); //if token valid then get userId from jwt token
                User user = userRepository.findByIdAndStatus(userId,1); // also get userId base user details from database

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getPhone());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //@INFO For globally set credentials over full application
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); //for full API

            }
        }catch (Exception e){

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);//after jwt authenticate user can be permited for API.


    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); //get authorizaytio value from http request header
        //String ip = request.getRemoteAddr();
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) { //token hasEmprty or not and Is token start with bearer token or not?
            return bearerToken.substring(7); //if valid substring 7 use kore first 7 character remove kora hoy.thn return jwt token
        }
        return null;  // if authorization haerder not valid then return null.
    }
}
