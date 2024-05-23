package com.jaax.springsecurity.controller;

import com.jaax.springsecurity.DTO.AuthResponse;
import com.jaax.springsecurity.DTO.AuthenticationRequest;
import com.jaax.springsecurity.DTO.ErrorResponse;
import com.jaax.springsecurity.DTO.RegisterRequest;
import com.jaax.springsecurity.exception.CustomAuthenticationException;
import com.jaax.springsecurity.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){//Crear un modelo/dto AuthResponse
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (CustomAuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.builder().errorMessage(e.getMessage()).build());
        }
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleCustomAuthenticationException(CustomAuthenticationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.builder().errorMessage(e.getMessage()).build());
    }
}
