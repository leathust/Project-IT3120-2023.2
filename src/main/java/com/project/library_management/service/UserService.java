package com.project.library_management.service;

import com.project.library_management.model.User;

public interface UserService {

    User findByUsername(String username);

    User save(User user);

}
