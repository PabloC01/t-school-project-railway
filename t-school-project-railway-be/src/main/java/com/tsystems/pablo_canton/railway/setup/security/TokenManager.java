package com.tsystems.pablo_canton.railway.setup.security;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenManager {
    private final Map<String, String> usernames = new HashMap<>();

    public String createTokenByUsername(String username) {
        String token = UUID.randomUUID().toString();
        usernames.put(token, username);
        return token;
    }

    public String getUsernameByToken(String token) {
        return usernames.get(token);
    }
}
