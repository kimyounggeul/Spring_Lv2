package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private long id; // 게시글 구분을 위한 id
    private String title; // 제목
    private String username; // 작성자명
    private String contents; // 작성내용
    private LocalDateTime modifiedAt; // 게시글 수정 날짜
    private LocalDateTime createAt; // 게시글 생성 날짜



    // Entity -> ResponseDto 변환
    // Board 라는 Entitiy에 저장값들을 호출해서 getxxx() 메서드를 이용해 BoardResponseDto의 필드에 담음
    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.username = board.getUsername();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }


}