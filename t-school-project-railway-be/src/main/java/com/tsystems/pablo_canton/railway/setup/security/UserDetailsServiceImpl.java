package com.tsystems.pablo_canton.railway.setup.security;

import com.tsystems.pablo_canton.railway.setup.exception.ResourceNotFoundException;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.UserEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found " + username));

        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setId(user.getUserId());
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setRole(user.getRole());

        return userDetails;
    }
}
