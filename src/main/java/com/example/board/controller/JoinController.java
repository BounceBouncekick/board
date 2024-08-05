package com.example.board.controller;

import com.example.board.dto.CommentCreateRequest;
import com.example.board.dto.UserDTO;
import com.example.board.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class JoinController {


    private final JoinService joinService;

    @GetMapping("/join")
    public String joinProcess(){
        return "join";
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinProcess(UserDTO userDTO) throws IOException {

        log.info("회원가입 유저 비밀번호 : {}", userDTO.getPassword());
        log.info("회원가입 유저 닉네임 : {}", userDTO.getUsername());

        joinService.joinProcess(userDTO);

        // /home.html로 리다이렉트
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/{boardId}")
//    public ResponseEntity<String> addComments(@PathVariable Long boardId, @ModelAttribute CommentCreateRequest req) {
//        commentService.writeComment(boardId, req);
//
//        return new ResponseEntity<>("댓글 작성 되었습니다.", HttpStatus.OK);
//    }


}