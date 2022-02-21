package com.example.rickandmorty.repository;

import com.example.rickandmorty.dto.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private final List<User> users;

    public UserRepository() {
        this.users = List.of(
                new User("user1", "userpass"),
                new User("user2", "passuser")
        );
    }

    public User getUserByLogin(String login) {
        return this.users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return this.users;
    }
}
