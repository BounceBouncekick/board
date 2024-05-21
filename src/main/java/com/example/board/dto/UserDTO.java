package com.example.board.dto;

import com.example.board.entity.UserEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class UserDTO {

    private String username;
    private String password;


    @Builder
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static UserDTO toEntity(UserEntity userEntity) {
        return UserDTO.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }

}