package com.project.library_management.service;

import com.project.library_management.model.Token;
import com.project.library_management.model.User;

public interface TokenService {

    Token findByToken(String jwt);

    Token saveToken(Token token);

    void saveUserToken(User user, String jwtToken);

    void revokeAllUserToken(Integer id);

    boolean revokeToken(String jwt);


}
