package com.sparta.board.controller;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.service.BoardSurvice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class BoardController {
    // BoardSurvice 와 연결
    private final BoardSurvice boardSurvice;

    public BoardController(BoardSurvice boardSurvice) {
        this.boardSurvice = boardSurvice;
    }

    @PostMapping("/post")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto){
       // 생성
        return boardSurvice.createBoard(requestDto);
    }

    @GetMapping("/post")
    public List<BoardResponseDto> getBoardList(){
        // DB 조회
        return boardSurvice.getBoardList();
    }

    @GetMapping("/post/{id}")
    public List<BoardResponseDto> getBoard(@PathVariable Long id) {
        return boardSurvice.getBoard(id);
    }


    @PutMapping("/post/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        // DB 내용 수정
        return boardSurvice.updateBoard(id, requestDto);
    }

    @DeleteMapping("/post/{id}")
    public BoardResponseDto deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
        // DB 내용 삭제
        return boardSurvice.deleteBoard(id, requestDto);
    }
}

