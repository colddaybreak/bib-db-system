package com.groupxxx.bibdb.service;

import com.groupxxx.bibdb.dto.LoginRequest;
import com.groupxxx.bibdb.dto.RegisterRequest;
import com.groupxxx.bibdb.dto.UserInfoDto;
import com.groupxxx.bibdb.model.entity.User;

public interface UserService {

    UserInfoDto register(RegisterRequest request);

    UserInfoDto login(LoginRequest request);

    User getUserEntity(Long id);

    User getUserEntityByUsername(String username);
}
