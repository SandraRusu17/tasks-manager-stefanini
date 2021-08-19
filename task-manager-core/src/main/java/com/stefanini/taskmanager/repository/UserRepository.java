package com.stefanini.taskmanager.repository;


import java.util.List;
import java.util.Optional;

import com.stefanini.taskmanager.entity.User;

public interface UserRepository {
    int saveUser(User user);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    void update(User user);
}
