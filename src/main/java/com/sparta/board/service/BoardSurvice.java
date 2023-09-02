package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.UserResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Spring 3 Layer Annotation 중 하나 (Controller, Service, Reository)
@Service
// 어노테이션을 이용한 생성자 주입
@RequiredArgsConstructor
@Slf4j
public class BoardSurvice {
    // BoardRepository 와 연결 인스턴스화
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    public BoardResponseDto createBoard(BoardRequestDto requestDto, String tokenValue) {
        //생성

        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);
        // 토큰 검증
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }
        // 토큰에서 사용자 정보 가져오기 (토큰에서 getBody로 Claims 가져옴)
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 username
        String username = info.getSubject();
        log.info(username);
        // 토큰에서 가져온 username,RequestDto -> Entity
        // requestDto 열쇠를 담아서 Board 객체로 변환
        Board board = new Board(username,requestDto);

        // DB 저장
        // 변환된 결과를 담은 board를 담아 boardRepository의
        // save 메서드를 호출해서 DB에 저장하고 그 결과값을 saveBoard 변수에 담는다
        Board saveBoard = boardRepository.save(board);

        // Entity -> ResponseDto
        // saveBoard 변수에 담긴 결과값을 담아서 boardResponseDto 객체로 변환
        BoardResponseDto boardResponseDto = new BoardResponseDto(saveBoard);

        // boardResponseDto를 반환
        return boardResponseDto;
    }

    public List<BoardResponseDto> getBoardList() {
        // DB조회
        // findAllBy   OrderBy     ModifiedAt     Desc()
        // 전체셀렉트     정렬          이 필드로      내림차순
        // stream().map(BoardResponseDto::new).toList(); BoardResponseDto 객체를 List 형태로 호출
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    public List<BoardResponseDto> getBoard(Long id) {
        // DB id로 조회

        // boardRepository의 findById 메서드로 매개변수로 넣은 해당 id 값을 찾는다
        return boardRepository.findById(id).stream().map(BoardResponseDto::new).toList();
    }

 //   @Transactional
//    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) {
        // DB에 존재 하는지 확인
 //       Board board = findBoard(id);

        // DB 내용 수정
        // 비밀 번호 일치여부 확인
        // board Entity에 저장된 password와 사용자가 requestDto로 입력한 password를
        // equals()메서드를 이용해서 일치 여부 학인
 //       if (board.getPassword().equals(requestDto.getPassword())) {
            //일치할 경우 board에 접근하여 requestDto 대로 update 한다
 //           board.update(requestDto);
 //     } else {
 //           return new BoardResponseDto("비밀번호가 일치하지 않습니다");
//        }
//        return new BoardResponseDto(board);
//    }

    public ResponseEntity<UserResponseDto> deleteBoard(Long id, String tokenValue) {

        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);
        // 토큰 검증
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Token Error");
        }
        // 토큰에서 사용자 정보 가져오기 (토큰에서 getBody로 Claims 가져옴)
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 username
        String username = info.getSubject();

        // DB에 존재 하는지 확인
        Board board = findBoard(id);
        if(board.getUsername().equals(username)) {
            UserResponseDto dto = new UserResponseDto();
            dto.setMsg("성공"); // msg란에 에러메시지 값으로 초기화
            dto.setStatusCode(String.valueOf(HttpStatus.OK.value())); // statusCode에 404 값으로 입력
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        else{
            UserResponseDto dto = new UserResponseDto();
            dto.setMsg("실패"); // msg란에 에러메시지 값으로 초기화
            dto.setStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value())); // statusCode에 404 값으로 입력
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
        }
    }

    // id 일치여부 확인
    // 수정과 삭제 공통 부분
    private Board findBoard(Long id){
        return boardRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다")
        );
    }


}
