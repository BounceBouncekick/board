package com.example.board.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String refresh;
    private String expiration;

    @Builder
    public RefreshEntity(Long id, String username, String refresh, String expiration) {
        this.id = id;
        this.username = username;
        this.refresh = refresh;
        this.expiration = expiration;
    }
}
