package com.example.Dingle.user.service;

import com.example.Dingle.user.dto.UserDTO;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.type.UserRole;
import com.example.Dingle.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegisterService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void registerUser(UserDTO userDTO) {
        String userId = userDTO.getUserId();
        String password = userDTO.getPassword();
        String userName = userDTO.getUsername();
        String phone = userDTO.getPhone();
        String email = userDTO.getEmail();

        boolean isExist = userRepository.existsByUserId(userId);

        if (isExist) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 userId 입니다.");
        }

        User data = new User();

        data.setUserId(userId);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setUsername(userName);
        data.setPhone(phone);
        data.setEmail(email);

        data.setUserRole(UserRole.USER);

        userRepository.save(data);
    }
}
