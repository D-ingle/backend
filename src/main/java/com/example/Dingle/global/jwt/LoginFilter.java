package com.example.Dingle.global.jwt;

import com.example.Dingle.global.dto.ResponseDTO;
import com.example.Dingle.user.dto.CustomUserDetails;
import com.example.Dingle.user.dto.UserInfoDTO;
import com.example.Dingle.user.service.UserInfoService;
import com.example.Dingle.user.type.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserInfoService userInfoService;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, UserInfoService userInfoService, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userInfoService = userInfoService;
        this.jwtUtil = jwtUtil;
        setUsernameParameter("userId");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String userId = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String userId = userDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        UserRole role = UserRole.valueOf(auth.getAuthority());

        String token = jwtUtil.createJWT(userId, role, 24L * 60 * 60 * 1000);

        UserInfoDTO userInfoDTO = userInfoService.getUserInfo(userId);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", userInfoDTO);

        ResponseDTO<Map<String, Object>> responseMap = new ResponseDTO<>(true, data, null);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Authorization", "Bearer " + token);

        new ObjectMapper().writeValue(response.getWriter(), responseMap);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
        response.setStatus(401);
    }
}
