package com.sparta.board.controller;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.UserResponseDto;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.service.BoardSurvice;
import com.sparta.board.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Spring 3 Layer Annotation 중 하나 (Controller, Service, Reository)
// 각 계층을 Bean으로 등록할 때 사용
// 해당 어노테이션 안에는 이미 @Component가 추가됨
// @Component 개발자가 직접 작성한 Class를 Bean으로 등록하기위한 어노테이션
@RestController
// 아래에서 구현된 각 API의 URL에 공통적으로 들어가는 부분
// 동일한 URL 부분의 반복을 줄여 줄 수 있다
@RequestMapping("/api")

public class BoardController {
    // BoardSurvice 와 연결 인스턴스화
    private final BoardSurvice boardSurvice;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtUtil jwtUtil;





    // 생성자
    public BoardController(BoardSurvice boardSurvice, JwtUtil jwtUtil) {
        this.boardSurvice = boardSurvice;
        this.jwtUtil = jwtUtil;


    }

    @PostMapping("/post")
    // BoardResponseDto 반환타입,  createBoard 메소드명(원하는대로)
    // @RequestBody : Post 안에 저장된 body 값들을 key:value 형태 (JSON 타입)으로 짝지음 body에 들어오는 데이터들을 가지고옴
    // BoardRequestDto : JSON 타입으로 넘어오는 데이터를 받는 객체(데이터를 저장할 공간)
    // requestDto : requestDto 매개변수에 데이터를 담아서, boardService의 createBoard 메서드로 실어보냄
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto,@CookieValue(AUTHORIZATION_HEADER) String tokenValue){
        // 생성 (requestbody부분의 데이터와 requestheader부분의 tokenvalue 받아옴)

        // 매개변수 requestDto 를 메소드 createBoard를 사용하여 boardSurvice로 반환(boardSurvice와 연결)
        return boardSurvice.createBoard(requestDto,tokenValue);
    }

    @GetMapping("/post")
    // List 형태로
    // BoardResponseDto 반환타입 getBoardList 메소드명 () 전부 Client에게 반환하므로 비워둠
    public List<BoardResponseDto> getBoardList(){
        // DB 조회
        // getBoardList 메소드를 사용하여 boardSurvice 와 연결
        return boardSurvice.getBoardList();
    }

    @GetMapping("/post/{id}")
    // List 형태로
    // BoardResponseDto 반환타입 getBoard 메소드명 (@PathVariable 게시글마다 생성되는 id 값을 넣기위해 사용 Long 타입 id)
    public List<BoardResponseDto> getBoard(@PathVariable Long id) {
        // 선택한 DB 내용 조회
        // getBoard 메소드를 사용하여 boardSurvice 와 연결
        return boardSurvice.getBoard(id);
    }


    @PutMapping("/post/{id}")
    // 수정을 위해 BoardResponseDto의 필드값이 필요
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto,@CookieValue(AUTHORIZATION_HEADER) String tokenValue) {
         //DB 내용 수정
        // requestDto의 id를 가지고옴
        BoardResponseDto boardResponseDto = boardSurvice.updateBoard(id, requestDto,tokenValue);
        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<UserResponseDto> deleteBoard(@PathVariable Long id,@CookieValue(AUTHORIZATION_HEADER) String tokenValue){
        // DB 내용 삭제
        return boardSurvice.deleteBoard(id, tokenValue);
    }
}


