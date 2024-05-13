package com.example.board.controller;

import com.example.board.dto.UserDTO;
import com.example.board.service.JoinService;
import com.example.board.userdetails.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JoinController {


    private final JoinService joinService;
    private final AuthenticationService authenticationService;

    @PostMapping("/join")
    public String joinProcess(@RequestBody UserDTO userDTO) throws IOException {
        log.info("회원가입 유저 닉네임 : {}",userDTO.getUsername());
        log.info("회원가입 유저 비밀번호 : {}",userDTO.getPassword());

        joinService.joinProcess(userDTO);


        return "ok";
    }

    @PostMapping("/board/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        log.info("로그인 유저네임 : {}", username);
        String password = userDTO.getPassword();
        log.info("로그인 패스워드 : {}", password);

        String token = authenticationService.authenticateUser(username, password);

        if (token != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            return ResponseEntity.ok().headers(headers).body("Login Successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
        }
    }
}