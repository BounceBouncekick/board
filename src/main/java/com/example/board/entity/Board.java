package com.example.board.entity;

import com.example.board.enumclass.BoardCategory;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "board")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "b_id")
    private Long id;

    private String title;   // 제목
    private String body;    // 본문

    private String uuid = UUID.randomUUID().toString();

    @Enumerated(EnumType.STRING)
    private BoardCategory category; // 카테고리

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;      // 작성자

    private int likeCount;  // 좋아요 수를 저장하는 필드

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "board_image_urls", joinColumns = @JoinColumn(name = "board_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>(); // 이미지 URL을 리스트로 저장

    public void addImageUrl(String imageUrl) {
        this.imageUrls.add(imageUrl); // 이미지 URL을 리스트에 추가
    }

    public List<String> getImageUrls() {
        return imageUrls;
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
