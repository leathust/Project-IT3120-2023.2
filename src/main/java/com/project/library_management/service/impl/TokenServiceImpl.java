package com.project.library_management.service.impl;

import com.project.library_management.model.Token;
import com.project.library_management.model.User;
import com.project.library_management.repository.TokenRepository;
import com.project.library_management.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public Token findByToken(String jwt) {
        return tokenRepository.findByToken(jwt).orElse(null);
    }

    @Override
    public Token saveToken(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserToken(Integer id) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(id);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public boolean revokeToken(String jwt) {
        var storedToken = tokenRepository.findByToken(jwt).orElse(null);
        if (storedToken != null) {
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            return true;
        }
        return false;
    }
}
