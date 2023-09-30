package com.BlogApplaction.backend.controllers;

import com.BlogApplaction.backend.Seurity.JwtTokenHelper;
import com.BlogApplaction.backend.exceptions.ApiException;
import com.BlogApplaction.backend.payload.JwtAuthRequest;
import com.BlogApplaction.backend.payload.JwtAuthResponse;
import com.BlogApplaction.backend.payload.UserDto;
import com.BlogApplaction.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){


        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse re = new JwtAuthResponse();
        re.setToken(token);

        return  new ResponseEntity<JwtAuthResponse>(re, HttpStatus.OK);
    }



    private void authenticate(String username,String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try{
            this.authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e) {
            System.out.println("Invalid Detials !!");
            throw new ApiException("Invalid username or password !!");
        }


    }


    // Creating Regester API

    @PostMapping("/register")
    public  ResponseEntity<UserDto> regesterUser(@RequestBody UserDto userDto){
        UserDto userDto1 = this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(userDto1,HttpStatus.CREATED);
    }


}
