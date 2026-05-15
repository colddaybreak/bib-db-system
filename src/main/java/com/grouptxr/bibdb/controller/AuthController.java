package com.grouptxr.bibdb.controller;

import com.grouptxr.bibdb.dto.ApiResponse;
import com.grouptxr.bibdb.dto.LoginRequest;
import com.grouptxr.bibdb.dto.RegisterRequest;
import com.grouptxr.bibdb.dto.UserInfoDto;
import com.grouptxr.bibdb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Register and login (no JWT in this build)")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Create a new user account")
    public ApiResponse<UserInfoDto> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.ok(userService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login with username and password")
    public ApiResponse<UserInfoDto> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok(userService.login(request));
    }
}
