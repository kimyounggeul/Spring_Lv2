package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// Spring 3 Layer Annotation 중 하나 (Controller, Service, Reository)
@Service
// 어노테이션을 이용한 생성자 주입
@RequiredArgsConstructor

public class BoardSurvice {
    // BoardRepository 와 연결 인스턴스화
    private final BoardRepository boardRepository;
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        //생성
        // RequestDto -> Entity
        // requestDto 열쇠를 담아서 Board 객체로 변환
        Board board = new Board(requestDto);

        // DB 저장
        // 변환된 결과를 담은 board를 담아 boardRepository의
        // save 메서드를 호출해서 DB에 저장하고 그 결과값을 saveBoard 변수에 담는다
        Board saveBoard = boardRepository.save(board);

        // Entity -> ResponseDto
        // saveBoard 변수에 담긴 결과값을 담아서 boardResponseDto 객체로 변환
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);

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

    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) {
        // DB에 존재 하는지 확인
        Board board = findBoard(id);

        // DB 내용 수정
        // 비밀 번호 일치여부 확인
        // board Entity에 저장된 password와 사용자가 requestDto로 입력한 password를
        // equals()메서드를 이용해서 일치 여부 학인
        if (board.getPassword().equals(requestDto.getPassword())) {
            //일치할 경우 board에 접근하여 requestDto 대로 update 한다
            board.update(requestDto);
        } else {
            return new BoardResponseDto("비밀번호가 일치하지 않습니다");
        }
        return new BoardResponseDto(board);
    }

    public BoardResponseDto deleteBoard(Long id, BoardRequestDto requestDto) {
        // DB에 존재 하는지 확인
        Board board = findBoard(id);

         // DB 내용 삭제
//        boardRepository.delete(board);
//        return id;
        if (board.getPassword().equals(requestDto.getPassword())) {
            //일치할 경우 boardRepository 접근하여 board 를 delete 한다
            boardRepository.delete(board);
        } else {
            return new BoardResponseDto("비밀번호가 일치하지 않습니다");
        }
        return new BoardResponseDto("게시글을 삭제했습니다");
    }

    // id 일치여부 확인
    // 수정과 삭제 공통 부분
    private Board findBoard(Long id){
        return boardRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다")
        );
    }
}
