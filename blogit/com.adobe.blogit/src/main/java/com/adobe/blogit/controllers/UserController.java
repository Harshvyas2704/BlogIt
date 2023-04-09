package com.adobe.blogit.controllers;

import com.adobe.blogit.payloads.ApiResponse;
import com.adobe.blogit.payloads.UserDto;
import com.adobe.blogit.servicies.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/users/")
    public ResponseEntity<UserDto> createNewUser(@Valid @RequestBody UserDto user){


        UserDto createdUser = this.userServices.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto user, @PathVariable Long userId){

        UserDto updatedUser = this.userServices.updateUser(user, userId);
        return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
        // Not Working
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){

        this.userServices.deleteUser(userId);
        return new ResponseEntity(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public  ResponseEntity<UserDto> getUserById(@PathVariable Long userId){

        return ResponseEntity.ok(this.userServices.getUserById(userId));
    }

    @GetMapping("/users/analytics/users")
    public String getTotalNumberOfUsers() {
        return userServices.getTotalNumberOfUsers();
    }

    @GetMapping("analytics/users/top-active")
    public ResponseEntity<List<UserDto>> getTop5ActiveUsers(){

        List<UserDto> userDtos = this.userServices.getTop5ActiveUsers();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }

}