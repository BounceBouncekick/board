package com.example.board.entity;

import com.example.board.EnumClass.BoardCategory;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "board")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;   // 제목
    private String body;    // 본문

    private String uuid = UUID.randomUUID().toString();

    @Enumerated(EnumType.STRING)
    private BoardCategory category; // 카테고리

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;      // 작성자

    private int likeCount;  // 좋아요 수를 저장하는 필드

    @Column(name = "image_urls", length = 1000) // 이미지 URL들을 저장할 문자열 컬럼
    private String imageUrls; // 이미지 URL을 쉼표(,)로 구분하여 하나의 문자열로 저장

    public void addImageUrl(String imageUrl) {
        if (this.imageUrls == null) {
            this.imageUrls = imageUrl;
        } else {
            this.imageUrls += "," + imageUrl; // 기존 이미지 URL 뒤에 추가
        }
    }

    @Builder
    public Board(Long id, String title, String body, BoardCategory category, UserEntity user,int likeCount) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.category = category;
        this.likeCount = likeCount;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        } else {
            throw new IllegalArgumentException("좋아요 수는 0보다 작을 수 없습니다.");
        }
    }
}
