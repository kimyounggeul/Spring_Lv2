package com.sparta.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // 스프링 서버가 뜰 때 IoC 컨테이너에 의해 bean으로 저장
public class PasswordConfig {

    @Bean // Bean을 이용해 passwordEncoder 메서드 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // PasswordEncoder의 구현체가 BCryptPasswordEncoder  이걸 사용해 패스워드 인코드(암호화)
    }
}