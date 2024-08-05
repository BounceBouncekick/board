package com.example.board.dto;


import com.example.board.enumclass.BoardCategory;
import com.example.board.entity.Board;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardCreateRequest {

    private String title;
    private String body;
    private MultipartFile uploadImage;
    private List<String> imageUrls; // 이미지 URL 리스트 추가
    private String uuid; // uuid 추가

    @Builder
    public BoardCreateRequest(String title, String body, MultipartFile uploadImage, List<String> imageUrls, String uuid) {
        this.title = title;
        this.body = body;
        this.uploadImage = uploadImage;
        this.imageUrls = imageUrls;
        this.uuid = uuid;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
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

    public static BoardCreateRequest create() {
        return new BoardCreateRequest();
    }
}
