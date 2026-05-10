package org.example.cpt402cw3.controller;

import org.example.cpt402cw3.common.ApiResponse;
import org.example.cpt402cw3.request.LoginRequest;
import org.example.cpt402cw3.request.RegisterRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "User Registration, Login")
public class AuthController {

    @PostMapping("/register")
    @Operation(summary = "User Registration")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.success("register ok");
    }

    @PostMapping("/login")
    @Operation(summary = "User Login")
    public ApiResponse<String> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success("fake-token-123456");
    }
}