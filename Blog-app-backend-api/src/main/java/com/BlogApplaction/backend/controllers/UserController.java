package com.BlogApplaction.backend.controllers;

import com.BlogApplaction.backend.payload.ApiResponce;
import com.BlogApplaction.backend.payload.UserDto;
import com.BlogApplaction.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;


    // post user means create User

    @PostMapping("/")
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto){

        UserDto user = this.service.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }


    // PUT- User means Update USer
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uid){
        UserDto userDto1 = this.service.updateUser(userDto, uid);

        return ResponseEntity.ok(userDto1);
    }




    // Delete Menas Delate User
    // Only Admin can Delete User we Implement with the Help of Global Method Security
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponce> deleteUser(@PathVariable("userId") Integer uid){
       this.service.deleteUser(uid);
       return new ResponseEntity<ApiResponce>(new ApiResponce("User Successfully Deleted",true), HttpStatus.OK);

    }


    // get all user

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getallUsers(){
        List<UserDto> allUsers = this.service.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    // get user using id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer uid){
        UserDto userById = this.service.getUserById(uid);
        return ResponseEntity.ok(userById);
    }
}
