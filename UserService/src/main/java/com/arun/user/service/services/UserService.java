package com.arun.user.service.services;

import com.arun.user.service.services.entities.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();

    //get Single User
    User getUser(String userId);
}
