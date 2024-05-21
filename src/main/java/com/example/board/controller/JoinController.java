package com.example.board.controller;

import com.example.board.dto.UserDTO;
import com.example.board.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Slf4j
@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/board")
public class JoinController {


    private final JoinService joinService;

    @GetMapping("/join")
    public String joinProcess(){
        return "join";
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(UserDTO userDTO) throws IOException {

        log.info("회원가입 유저 비밀번호 : {}",userDTO.getPassword());
        log.info("회원가입 유저 닉네임 : {}",userDTO.getUsername());


        joinService.joinProcess(userDTO);


        return new ResponseEntity<>("회원가입완료", HttpStatus.OK);
    }


}