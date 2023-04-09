package com.adobe.blogit.servicies.impl;

import com.adobe.blogit.exceptions.ResourceNotFoundException;
import com.adobe.blogit.models.User;
import com.adobe.blogit.payloads.UserDto;
import com.adobe.blogit.repositories.UserRepo;
import com.adobe.blogit.servicies.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto user) {

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User newUSer = this.dtoToUser(user);
        User createdUser = this.userRepo.save(newUSer);
        return this.userToDto(createdUser);

    }

    @Override
    public UserDto updateUser(UserDto user, Long userId) {

        User getUser = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User Id", userId));

        getUser.setName(user.getName());
        getUser.setUpdatedAt(LocalDateTime.now());
        User updatedUser = this.userRepo.save(getUser);
        UserDto userDto = this.userToDto(updatedUser);

        userDto.setNoOfPosts(updatedUser.getPosts().size());
        return userDto;

    }

    @Override
    public UserDto getUserById(Long userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User Id", userId));
        UserDto userDto = this.userToDto(user);

        userDto.setNoOfPosts(user.getPosts().size());

        return userDto;

    }

    @Override
    public String getTotalNumberOfUsers() {
        return "Total number of Users are: " + userRepo.count();
    }

    @Override
    public void deleteUser(Long userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User Id", userId));

        this.userRepo.delete(user);

    }

    @Override
    public List<UserDto> getTop5ActiveUsers() {

        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for(int i = 0; i < users.size(); i++){

            UserDto userDto = this.userToDto(users.get(i));
            userDto.setNoOfPosts(users.get(i).getPosts().size());
            userDtos.add(userDto);

        }

        Collections.sort(userDtos, Comparator.comparingInt(UserDto::getNoOfPosts).reversed());

        List<UserDto> topFiveUsers = new ArrayList<>();
        int i = 0;
        while (i < userDtos.size()){

            if(i == 5){
                break;
            }

            topFiveUsers.add(userDtos.get(i));
            i++;

        }

        return topFiveUsers;

    }

    public UserDto userToDto(User user){
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }
}
