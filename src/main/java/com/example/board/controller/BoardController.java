package com.example.board.controller;

import com.example.board.enumclass.BoardCategory;
import com.example.board.dto.BoardCreateRequest;
import com.example.board.dto.BoardDetailResponse;
import com.example.board.service.BoardService;
import com.example.board.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/write")
    public String writePage(Model model) {
        model.addAttribute("boardCreateRequest", BoardCreateRequest.create());
        model.addAttribute("selectedCategory", "new-category"); // 새로운 카테고리 설정
        return "write";
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

    @GetMapping("/delete/{uuid}")
    public String boardDelete(@PathVariable String uuid, Model model) {
        boardService.delete(uuid);
        model.addAttribute("message", "삭제완료");
        return "redirect:/boards/List"; // 삭제 후 게시판 목록으로 리다이렉트
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

    @PostMapping("/like/{boardId}")
    @ResponseBody // 이 부분 추가
    public ResponseEntity<Integer> addLike(@PathVariable Long boardId) {
        log.info("likeboardId : {}", boardId);
        int newLikeCount = likeService.addLike(boardId);
        return ResponseEntity.ok(newLikeCount); // 좋아요 수를 반환
    }

    @GetMapping("/unlike/{boardId}")
    public ResponseEntity<String> unlike(@PathVariable Long boardId) {
        likeService.unlike(boardId);
        return new ResponseEntity<>("좋아요 취소", HttpStatus.OK);
    }

    @GetMapping("/List")
    public String findAll(Model model){
        List<BoardCreateRequest> boardDTOList = boardService.findAll();
        model.addAttribute("boardList",boardDTOList);
        return "List";
    }

    @GetMapping("/details/{uuid}")
    public String getBoardDetail(@PathVariable String uuid, Model model) {
        BoardDetailResponse boardDetail = boardService.getBoardDetail(uuid);
        model.addAttribute("board", boardDetail);
        return "details"; // 여기서 'boards/details'는 src/main/resources/templates/boards/details.html을 의미합니다.
    }
}
