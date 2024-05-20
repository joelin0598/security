package com.jaax.springsecurity.controller;

import com.jaax.springsecurity.DTO.AuthResponse;
import com.jaax.springsecurity.DTO.AuthenticationRequest;
import com.jaax.springsecurity.DTO.RegisterRequest;
import com.jaax.springsecurity.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthenticationRequest request){//Crear un modelo/dto AuthenticationRequest
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
