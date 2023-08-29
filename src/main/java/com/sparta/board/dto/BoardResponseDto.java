package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private long id;
    private String title;
    private String username;
    private String contents;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    private String msg;

    // Entity -> ResponseDto 변환
    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.username = board.getUsername();
        this.title = board.getTitle();
        this.password = board.getPassword();
        this.contents = board.getContents();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    public BoardResponseDto(String msg){
        this.msg = msg;
    }
}