package com.example.board.service;


import com.example.board.exception.CommentNotFoundException;
import com.example.board.dto.CommentCreateRequest;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    public CommentService(BoardRepository boardRepository, CommentRepository commentRepository) {
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    public void writeComment(Long boardId, CommentCreateRequest req) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        Comment comment = req.toEntity(board, req);
        commentRepository.save(comment);
    }

    @Transactional
    public void editComment(Long commentId, String newBody) {
        boolean exists = commentRepository.existsById(commentId);
        if (exists) {
            Optional<Comment> commentOptional = commentRepository.findById(commentId);
            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                comment.update(newBody); // Comment 엔티티의 update 메서드 호출
                commentRepository.save(comment); // 변경 사항 저장
            }
        } else {
            throw new CommentNotFoundException("해당 ID로 댓글을 찾을 수 없습니다: " + commentId);
        }
    }

    @Transactional
    public void deleteComment(Long commentId) {
        boolean exists = commentRepository.existsById(commentId);
        if (exists) {
            commentRepository.deleteById(commentId);
        } else {
            throw new CommentNotFoundException("해당 ID로 댓글을 찾을 수 없습니다: " + commentId);
        }
    }
}
