package com.example.board.dto;

import com.example.board.enumclass.BoardCategory;
import com.example.board.entity.Board;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BoardDetailResponse {
    private Long id;
    private String title;
    private String body;
    private BoardCategory category;
    private List<String> imageUrls;
    private int likeCount;
    private String uuid;

    @Builder
    public BoardDetailResponse(Long id, String title, String body, BoardCategory category, List<String> imageUrls, int likeCount, String uuid) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.category = category;
        this.imageUrls = imageUrls;
        this.likeCount = likeCount;
        this.uuid = uuid;
    }

    public static BoardDetailResponse fromEntity(Board board) {
        return BoardDetailResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .body(board.getBody())
                .category(board.getCategory())
                .imageUrls(board.getImageUrls()) // 이미지를 불러오는 코드
                .likeCount(board.getLikeCount())
                .uuid(board.getUuid())
                .build();
    }
}
