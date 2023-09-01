package com.sparta.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    // Bean으로 등록하고자 하는 객체를 반환하는 메서드
    // 메서드의 클래스 위에 @Configuration 해야함
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //BCrypt 비밀번호를 암호화 해주는 Hash 함수
    }
}