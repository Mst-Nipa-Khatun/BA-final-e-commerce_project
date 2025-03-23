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
            String jwt = getJwtFromRequest(httpServletRequest);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.isValidateToken(jwt,httpServletRequest)) {
                Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
                User user = userRepository.findByIdAndStatus(userId,1);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getPhone());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //@INFO For globally set credentials over full application
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }catch (Exception e){

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);


    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        //String ip = request.getRemoteAddr();
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
