package com.example.board.entity;

import com.example.board.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "board_user_entity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String category;
    private String role;

    private UserEntity() {

    }

    public static UserEntity toDTO(UserDTO userDTO){
        return UserEntity.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .category(userDTO.getCategory())
                .role("ROLE_ADMIN")
                .build();
    }


    public static class Builder {
        private String username;
        private String password;
        private String role;

        private String category;

        public Builder() {
            // 기본 생성자
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }
        public Builder category(String category){
            this.category = category;
            return this;
        }

        public UserEntity build() {
            UserEntity user = new UserEntity();
            user.username = this.username;
            user.password = this.password;
            user.role = this.role;
            return user;
        }
    }

    // UserEntity 클래스의 인스턴스를 생성하기 위한 정적 메서드
    public static Builder builder() {
        return new Builder();
    }
}
