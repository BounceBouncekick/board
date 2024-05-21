package com.example.board.service;

import com.example.board.Exception.BoardNotFoundException;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final BoardRepository boardRepository;

    public LikeService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public void addLike(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("해당 ID로 게시판을 찾을 수 없습니다: " + boardId));
        board.incrementLikeCount();
        boardRepository.save(board);  // 변경 사항 저장
    }

    @Transactional
    public void unlike(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("해당 ID로 게시판을 찾을 수 없습니다: " + boardId));
        board.decrementLikeCount();
        boardRepository.save(board);  // 변경 사항 저장
    }
}
