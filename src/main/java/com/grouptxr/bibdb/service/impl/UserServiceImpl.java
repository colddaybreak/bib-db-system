package com.groupxxx.bibdb.service.impl;

import com.groupxxx.bibdb.dto.LoginRequest;
import com.groupxxx.bibdb.dto.RegisterRequest;
import com.groupxxx.bibdb.dto.UserInfoDto;
import com.groupxxx.bibdb.model.entity.User;
import com.groupxxx.bibdb.model.repository.UserRepository;
import com.groupxxx.bibdb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserInfoDto register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        User u =
                User.builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .email(request.getEmail())
                        .build();
        userRepository.save(u);
        return new UserInfoDto(u.getId(), u.getUsername(), u.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoDto login(LoginRequest request) {
        User u =
                userRepository
                        .findByUsername(request.getUsername())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), u.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        return new UserInfoDto(u.getId(), u.getUsername(), u.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserEntity(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserEntityByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
