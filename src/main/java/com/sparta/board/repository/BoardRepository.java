package com.sparta.board.repository;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//JpaRepository<Board,           Long>
//              @Entity 클래스    id 데이터 타입
public interface BoardRepository extends JpaRepository<Board, Long> {

    // findAllBy   OrderBy     ModifiedAt     Desc()
    // 전체셀렉트     정렬          이 필드로      내림차순.
    // Memo 테이블에서 ModifiedAt 즉, 수정 시간을 기준으로 전체 데이터를
    // 내림차순으로 가져오는 SQL을 실행하는 메서드
    List<Board> findAllByOrderByModifiedAtDesc();

    Optional<Board> findByUsername(String username);


    //Optional<Board> findByUsername(String username);
}
