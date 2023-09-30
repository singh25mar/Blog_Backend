package com.BlogApplaction.backend.service.impl;

import com.BlogApplaction.backend.config.AppConstants;
import com.BlogApplaction.backend.entities.Role;
import com.BlogApplaction.backend.entities.User;
import com.BlogApplaction.backend.exceptions.ResourceNotFoundException;
import com.BlogApplaction.backend.payload.UserDto;
import com.BlogApplaction.backend.repository.RoleRepo;
import com.BlogApplaction.backend.repository.UserRepo;
import com.BlogApplaction.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private ModelMapper modelMapper;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RoleRepo roleRepo;


    // Regester User Varmy Importenet
    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        // Password Encode
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        // Assining The role
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);

        User save = this.repo.save(user);


        return this.modelMapper.map(save,UserDto.class);
    }




    //--------------------------------------------------Reg USer End --------------------------
    @Override
    public UserDto createUser(UserDto user) {
        User user1 = this.userdtoTOuser(user);
        User save = this.repo.save(user1);
        return this.userTouserDto(save);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user1 = this.repo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        user1.setId(userDto.getId());
        user1.setName(userDto.getName());
        user1.setEmail(userDto.getEmail());
        user1.setPassword(userDto.getPassword());
        user1.setAbout(userDto.getAbout());

        User save = this.repo.save(user1);
        UserDto userDto1 = this.userTouserDto(save);

        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user1 = this.repo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        UserDto userDto = this.userTouserDto(user1);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = repo.findAll();

        List<UserDto> userDtos = users.stream().map(user -> this.userTouserDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user1 = this.repo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        this.repo.delete(user1);

    }



    /////////////////////////////////////////////////////////
    private User userdtoTOuser(UserDto user){

        User user1 = this.modelMapper.map(user, User.class);
//        User user1 = new User();
//        user1.setId(user.getId());
//        user1.setName(user.getName());
//        user1.setEmail(user.getEmail());
//        user1.setPassword(user.getPassword());
//        user1.setAbout(user.getAbout());
        return user1;
    }


    private UserDto userTouserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());
        return  userDto;
    }





}
