package com.example.board.dto;

import com.example.board.entity.UserEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserDTO {

    private String username;
    private String password;
    private String category;

    public static UserDTO toEntity(UserEntity userEntity) {
        return UserDTO.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .category(userEntity.getCategory())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String username;
        private String password;

        private String category;
        public Builder() {}

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder category(String category){
            this.category = category;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(username, password,category);
        }
    }
}