package com.example.board.entity;

import com.example.board.EnumClass.UserRole;
import com.example.board.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board_user")
@Setter
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    private String role;

    @Builder
    private UserEntity(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }



    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Board> boards;     // 작성글

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comment> comments; // 댓글



    public static UserEntity toDTO(UserDTO userDTO) {
        return UserEntity.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role("ROLE_ADMIN")
                .build();
    }

}

    //    private UserEntity() {
//
//    }
//    public static class Builder {
//        private String username;
//        private String password;
//        private String role;
//
//        private String category;
//
//        public Builder() {
//            // 기본 생성자
//        }
//
//        public Builder username(String username) {
//            this.username = username;
//            return this;
//        }
//
//        public Builder password(String password) {
//            this.password = password;
//            return this;
//        }
//
//        public Builder role(String role) {
//            this.role = role;
//            return this;
//        }
//
//
//        public UserEntity build() {
//            UserEntity user = new UserEntity();
//            user.username = this.username;
//            user.password = this.password;
//            user.role = this.role;
//            return user;
//        }
//    }
//
//    // UserEntity 클래스의 인스턴스를 생성하기 위한 정적 메서드
//    public static Builder builder() {
//        return new Builder();
//    }

