package com.tsystems.pablo_canton.railway.rest;

import com.tsystems.pablo_canton.railway.business.dto.AuthDTO;
import com.tsystems.pablo_canton.railway.business.dto.LoginRequestDto;
import com.tsystems.pablo_canton.railway.business.dto.UserDTO;
import com.tsystems.pablo_canton.railway.setup.exception.PasswordIncorrectException;
import com.tsystems.pablo_canton.railway.setup.exception.ResourceNotFoundException;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.UserEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.repository.UserRepository;
import com.tsystems.pablo_canton.railway.setup.exception.UsernameAlreadyExistsException;
import com.tsystems.pablo_canton.railway.setup.security.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthRestAPIV1 {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthDTO> login(@RequestBody LoginRequestDto loginRequestDto){
        UserEntity user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found " + loginRequestDto.getUsername()));

        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new PasswordIncorrectException("User password does not match");
        }

        String token = tokenManager.createTokenByUsername(user.getUsername());

        AuthDTO dto = new AuthDTO();
        dto.setToken(token);
        dto.setRole(user.getRole());

        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "/sing-up")
    public ResponseEntity<AuthDTO> singUp(@RequestBody UserDTO userDTO){
        UserEntity user = createUser(userDTO);

        if(userRepository.existsUserEntityByUsername(user.getUsername())){
            throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " is not available");
        }

        user = userRepository.save(user);

        String token = tokenManager.createTokenByUsername(user.getUsername());

        AuthDTO dto = new AuthDTO();
        dto.setToken(token);
        dto.setRole(user.getRole());

        return ResponseEntity.ok(dto);
    }

    public UserEntity createUser(UserDTO userDTO){
        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setBirthDate(userDTO.getBirthDate());

        return user;
    }
}
