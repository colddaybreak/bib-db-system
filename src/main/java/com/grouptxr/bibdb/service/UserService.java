package com.grouptxr.bibdb.service;

import com.grouptxr.bibdb.dto.LoginRequest;
import com.grouptxr.bibdb.dto.RegisterRequest;
import com.grouptxr.bibdb.dto.UserInfoDto;
import com.grouptxr.bibdb.model.entity.User;

public interface UserService {

    UserInfoDto register(RegisterRequest request);

    UserInfoDto login(LoginRequest request);

    User getUserEntity(Long id);

    User getUserEntityByUsername(String username);
}
