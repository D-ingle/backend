package com.example.Dingle.user.service;

import com.example.Dingle.user.dto.CustomUserDetails;
import com.example.Dingle.user.entity.UserEntity;
import com.example.Dingle.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다." + userId));

        if (userData == null) {
            throw new UsernameNotFoundException(userId);
        }

        return new CustomUserDetails(userData);
    }
}
