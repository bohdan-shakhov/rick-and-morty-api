package com.example.rickandmorty.service;

import com.example.rickandmorty.entity.Role;
import com.example.rickandmorty.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();
}
