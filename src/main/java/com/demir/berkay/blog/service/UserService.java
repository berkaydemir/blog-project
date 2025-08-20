package com.demir.berkay.blog.service;

import java.util.List;
import java.util.Optional;
import com.demir.berkay.blog.model.User;

public interface UserService {

    User registerUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    void deleteUser(Long id);
    
}
