package com.example.board.controller;


import com.example.board.dto.CommentCreateRequest;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;

    public CommentController(CommentService commentService, BoardService boardService) {
        this.commentService = commentService;
        this.boardService = boardService;
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<String> addComments(@PathVariable Long boardId, @ModelAttribute CommentCreateRequest req) {
        commentService.writeComment(boardId, req);

        return new ResponseEntity<>("댓글 작성 되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/{commentId}/edit")
    public ResponseEntity<String> editComment(@PathVariable Long commentId, @ModelAttribute CommentCreateRequest req) {
        commentService.editComment(commentId, req.getBody());

        return new ResponseEntity<>("댓글 수정되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/{commentId}/delete")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return new ResponseEntity<>("댓글 삭제되었습니다.", HttpStatus.OK);
    }

}
