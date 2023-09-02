package com.sparta.board.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardRequestDto {
    //사용자가 요청한 데이터(입력한 값)

    private String title;

    private String contents;


}
