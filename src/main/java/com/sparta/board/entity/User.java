package com.sparta.board.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }
}

//    @Column(nullable = false, unique = true)
//   private String email;

//    @Column(nullable = false)
    // EnumTyp을 데이터베이스 컬럼에 저장할때 사용
//    @Enumerated(value = EnumType.STRING)
//    private UserRoleEnum role;

