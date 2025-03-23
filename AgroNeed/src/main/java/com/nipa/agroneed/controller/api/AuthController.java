package com.nipa.agroneed.controller.api;


import com.nipa.agroneed.dto.LoginDto;
import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.UserDto;
import com.nipa.agroneed.service.Impl.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*",
        methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST,
                RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginDto loginDto, HttpServletRequest httpServletRequest) {
        return authService.login(loginDto, httpServletRequest);
    }

    @PostMapping("/register")
    public Response register(@RequestBody UserDto userDto) {
        return authService.register(userDto);
    }

}