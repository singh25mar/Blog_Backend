package com.BlogApplaction.backend.service;

import com.BlogApplaction.backend.payload.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto user);         // Regester new user


    UserDto createUser(UserDto user);   //  create new User

    UserDto updateUser(UserDto user, Integer userId); // Update USer

    UserDto getUserById(Integer userId); // find user by user id

    List<UserDto> getAllUsers();  // get all user list

    void deleteUser(Integer userId);  // delete User
}
