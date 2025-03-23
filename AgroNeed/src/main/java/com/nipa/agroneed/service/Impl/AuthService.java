package com.nipa.agroneed.service.Impl;


import com.nipa.agroneed.config.JwtTokenProvider;
import com.nipa.agroneed.dto.LoginDto;
import com.nipa.agroneed.dto.LoginResponseDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.UserDto;
import com.nipa.agroneed.entity.User;
import com.nipa.agroneed.repository.UserRepository;
import com.nipa.agroneed.utils.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public Response login(LoginDto loginDto, HttpServletRequest httpServletRequest) {
        User user = userRepository.findByPhoneAndStatus(loginDto.getPhone(), 1);
        if (user == null) {
            return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST,
                    null, "Invalid Phone or Password");
        }
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getPhone(),
                        loginDto.getPassword()));
        if (authentication.isAuthenticated()) {
            LoginResponseDto loginResponseDto = new LoginResponseDto();
                loginResponseDto.setToken(jwtTokenProvider.generateToken(authentication, httpServletRequest));
            loginResponseDto.setUsername(user.getName());
            return ResponseBuilder.getFailResponse(HttpStatus.OK,
                    loginResponseDto, "Login Successful");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST,
                null, "Invalid Phone or Password");
    }

    public Response register(UserDto userDto) {
        User user = userRepository.findByPhoneAndStatus(userDto.getPhone(), 1);
        if (user == null) {
            user = new User();
            user.setStatus(1);
            user.setName(userDto.getUsername());
            user.setPhone(userDto.getPhone());
            String encodedPass = passwordEncoder.encode(userDto.getPassword());
            user.setPasswordHash(encodedPass);
            user.setEmail(userDto.getEmail());
            User savedUser = userRepository.save(user);

            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, null,
                    "Register Successful");
        }
        return ResponseBuilder.getFailResponse(HttpStatus.BAD_REQUEST, null, "Exist user using this phone");
    }
}
