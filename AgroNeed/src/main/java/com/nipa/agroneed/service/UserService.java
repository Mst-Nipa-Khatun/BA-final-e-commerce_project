package com.nipa.agroneed.service;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.UsersDto;

public interface UserService {
    Response createUser(UsersDto usersDto);
}
