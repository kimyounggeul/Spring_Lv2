package com.sparta.board.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardRequestDto {
    private long id;
    private String title;
    private String username;
    private String contents;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
