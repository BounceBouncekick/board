package com.example.board.userdetails;

import com.example.board.JWT.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public AuthenticationService(AuthenticationManager authenticationManager, JWTUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public String authenticateUser(String username, String password) {
        log.info("authenticationUser_username : {}", username);
        log.info("authenticationUser_password : {}", password);
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            log.info("Authentication: " + authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.info("UserDetails: " + userDetails);

            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse(""); // 권한이 없을 경우 빈 문자열 반환

            log.info("authenticationUser role : {}",role);

            return jwtUtil.createJwt(username, role, 60 * 60 * 10L); // 권한만 전달
        } catch (AuthenticationException e) {
            log.info("Authentication failed: " + e.getMessage());
            return null; // 인증 실패 시 null 반환
        }
    }
}
