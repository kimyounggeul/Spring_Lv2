package com.sparta.board.repository;

import com.sparta.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByModifiedAtDesc(); // Memo 테이블에서 ModifiedAt 즉, 수정 시간을 기준으로 전체 데이터를 내림차순으로 가져오는 SQL을 실행하는 메서드
}
