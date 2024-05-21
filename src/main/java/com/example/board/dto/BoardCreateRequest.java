package com.example.board.dto;


import com.example.board.EnumClass.BoardCategory;
import com.example.board.entity.Board;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardCreateRequest {

    private String title;
    private String body;
    private MultipartFile uploadImage;

    @Builder
    public BoardCreateRequest(String title, String body, MultipartFile uploadImage) {
        this.title = title;
        this.body = body;
        this.uploadImage = uploadImage;
    }

    public void uploadImage(MultipartFile uploadImage) {
        this.uploadImage = uploadImage;
    }
    public Board toEntity(BoardCategory category) {
        return Board.builder()
                .category(category)
                .title(title)
                .body(body)
                .build();
    }

    public static BoardCreateRequest of() {
        return new BoardCreateRequest();
    }
}
