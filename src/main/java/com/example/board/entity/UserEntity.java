package com.example.board.entity;

import com.example.board.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board_user")
public class UserEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "아이디는 필수 항목입니다.")
    @Size(min=2, message = "아이디는 최소 두 글자 이상입니다")
    @Column(nullable = false,unique = true)
    private String username;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
//    @Pattern(regexp = " ^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
//            message = "8자 이상이며 최대 20자까지 허용. 반드시 숫자, 문자 포함")
    @Column(nullable = false)
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

