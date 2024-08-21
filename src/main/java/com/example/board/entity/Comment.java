package com.example.board.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;

    @Column(name = "c_body")
    private String body;

    @Builder
    public Comment(Long id, String body, UserEntity user, Board board) {
        this.id = id;
        this.body = body;
        this.user = user;
        this.board = board;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;      // 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;    // 댓글이 달린 게시판

    public void update(String newBody) {
        this.body = newBody;
    }
}
