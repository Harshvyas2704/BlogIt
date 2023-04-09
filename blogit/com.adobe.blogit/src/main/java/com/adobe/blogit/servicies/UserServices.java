package com.adobe.blogit.servicies;

import com.adobe.blogit.models.User;
import com.adobe.blogit.payloads.UserDto;

import java.util.List;

public interface UserServices {

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Long userId);
    UserDto getUserById(Long userId);
    String getTotalNumberOfUsers();
    void deleteUser(Long userId);
    List<UserDto> getTop5ActiveUsers();

}
