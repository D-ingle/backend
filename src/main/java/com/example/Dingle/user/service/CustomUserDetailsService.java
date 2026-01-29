package com.example.Dingle.user.service;

import com.example.Dingle.global.exception.AuthException;
import com.example.Dingle.global.message.AuthErrorMessage;
import com.example.Dingle.user.dto.CustomUserDetails;
import com.example.Dingle.user.entity.User;
import com.example.Dingle.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.Dingle.global.message.BusinessErrorMessage.USER_NOT_EXISTS;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User userData = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_EXISTS + userId));

        if (userData == null) {
            throw new AuthException(AuthErrorMessage.USER_NOT_EXIST);
        }

        return new CustomUserDetails(userData);
    }
}
