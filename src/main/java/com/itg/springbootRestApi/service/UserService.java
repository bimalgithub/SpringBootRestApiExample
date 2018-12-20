package com.itg.springbootRestApi.service;

import java.util.List;
import java.util.Optional;

import com.itg.springbootRestApi.model.User;


public interface UserService {

   Optional<User> findById(long id);

    User findByName(String name);

    List<User> findAllUsers();

    User saveUser(User user);

    void updateUser(User user);

    void deleteUserById(long id);

    void deleteAllUsers();

    boolean isUserExist(User user);

}
