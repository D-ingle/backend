package com.example.Dingle.user.service;

import com.example.Dingle.user.dto.UserDTO;
import com.example.Dingle.user.entity.UserEntity;
import com.example.Dingle.user.entity.UserRole;
import com.example.Dingle.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

        if (isExist) { return; }

        UserEntity data = new UserEntity();

        data.setUserId(userId);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setUsername(userName);
        data.setPhone(phone);
        data.setEmail(email);

        data.setUserRole(UserRole.USER);

        userRepository.save(data);
    }
}
