package com.nipa.agroneed.service.Impl;

import com.nipa.agroneed.dto.Response;
import com.nipa.agroneed.dto.UsersDto;
import com.nipa.agroneed.entity.UserEntity;
import com.nipa.agroneed.repository.UserRepository;
import com.nipa.agroneed.service.UserService;
import com.nipa.agroneed.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response createUser(UsersDto usersDto) {
        UserEntity users=userRepository.findByNameAndStatus(usersDto.getName(), 1);
        if(users==null){
            users=new UserEntity();
            users.setStatus(1);
            users.setName(usersDto.getName());
            users.setAddress(usersDto.getAddress());
            users.setEmail(usersDto.getEmail());
            users.setPasswordHash(usersDto.getPasswordHash());
            users.setRole(usersDto.getRole());
            UserEntity savedUsers=userRepository.save(users);
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED,savedUsers,"Successfully created user");


        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.BAD_REQUEST,null,"User already exists");
    }
}
