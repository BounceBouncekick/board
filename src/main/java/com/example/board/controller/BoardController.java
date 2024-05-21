package com.example.board.controller;


import com.example.board.EnumClass.BoardCategory;
import com.example.board.dto.BoardCreateRequest;
import com.example.board.service.BoardService;
import com.example.board.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    private final LikeService likeService;

    public BoardController(BoardService boardService,LikeService likeService) {
        this.boardService = boardService;
        this.likeService = likeService;
    }

    @PostMapping("/{category}")
    public ResponseEntity<String> boardWrite(@PathVariable String category, @ModelAttribute BoardCreateRequest req) throws IOException {
        BoardCategory boardCategory = BoardCategory.of(category);
        log.info("boardWrite_boardCategory :  {}", boardCategory);
        log.info("req :{}", req.getBody());
        log.info("req :{}", req.getTitle());
        log.info("req :{}", req.getUploadImage());

        boardService.writeBoard(req, boardCategory);

        return new ResponseEntity<>("등록완료", HttpStatus.OK);
    }

    @GetMapping("delete/{uuid}")
    public ResponseEntity<?> boardDelete(@PathVariable String uuid) {
        boardService.delete(uuid);

        return new ResponseEntity<>("삭제완료", HttpStatus.OK);
    }

    @GetMapping("/update/{uuid}")
    public ResponseEntity<String> updatebyUuid(@PathVariable String uuid, @ModelAttribute BoardCreateRequest req,
                                               @RequestParam(value = "image", required = false) MultipartFile imageFile)throws IOException {

        log.info("컨트롤러 이미지 파일 수정 : {}", imageFile);
        try {
            boardService.update(uuid, req, imageFile);
            return new ResponseEntity<>("수정완료", HttpStatus.OK);
        }  catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        } catch (IOException e) {
            // 파일 업로드 중에 발생하는 예외 처리
            return new ResponseEntity<>("파일 업로드 중에 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/like/{boardId}")
    public ResponseEntity<String> addLike(@PathVariable Long boardId) {
        likeService.addLike(boardId);
        return new ResponseEntity<>("좋아요", HttpStatus.OK);
    }

    @GetMapping("/unlike/{boardId}")
    public ResponseEntity<String> unlike(@PathVariable Long boardId) {
        likeService.unlike(boardId);
        return new ResponseEntity<>("좋아요 취소", HttpStatus.OK);
    }
}
