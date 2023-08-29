package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class BoardSurvice {
    // BoardRepository 와 연결
    private final BoardRepository boardRepository;
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        //생성
        // RequestDto -> Entity
        Board board = new Board(requestDto);

        // DB 저장
        Board saveBoard = boardRepository.save(board);

        // Entity -> ResponseDto
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);

        return boardResponseDto;
    }

    public List<BoardResponseDto> getBoardList() {
        //DB조회
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    public List<BoardResponseDto> getBoard(Long id) {
        // DB id로 조회
        return boardRepository.findById(id).stream().map(BoardResponseDto::new).toList();
    }

    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto) {
        // DB에 존재 하는지 확인
        Board board = findBoard(id);

        // DB 내용 수정
//        board.update(requestDto);
//        return id;
        if (board.getPassword().equals(requestDto.getPassword())) {
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
            boardRepository.delete(board);
        } else {
            return new BoardResponseDto("비밀번호가 일치하지 않습니다");
        }

        return new BoardResponseDto("게시글을 삭제했습니다");
    }

    private Board findBoard(Long id){
        return boardRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다")
        );
    }
}
