package com.project.library_management.service.impl;

import com.project.library_management.model.User;
import com.project.library_management.repository.UserRepository;
import com.project.library_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
