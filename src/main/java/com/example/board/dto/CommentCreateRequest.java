package com.example.board.dto;

import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
public class CommentCreateRequest {

    private String body;

    @Builder
    public CommentCreateRequest(String body) {
        this.body = body;
    }

    public Comment toEntity(Board board,CommentCreateRequest req){
        return Comment.builder()
                .board(board)
                .body(body)
                .build();
    }
}
