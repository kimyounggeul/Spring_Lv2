package com.sparta.board.entity;

import com.sparta.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// JPA가 관리 할 수 있는 Entity 클래스로 지정
@Entity
@Getter
@Setter

// 매핑할 테이블 이름을 지정
@Table(name = "board")
// 어노테이션을 이용한 생성자 주입
@NoArgsConstructor
public class Board extends Timestamped {
    // 필드, 기본키 지정
    @Id
    // 기본 키값을 자동으로 생성하는 전략을 지정할때 사용
    // 데이터베이스의 자동증가 기능을 통해 id 값이 자동으로 생성되고 할당됨
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 필드를 데이터베이스 컬럼과 매핑할때 사용
    // name : 해당 필드를 어떤 컬럼과 매핑할것인지 지정 title 필드가 데이터베이스 테이블의 title 컬럼과 매핑됨
    // nullable 컬럼이 null 값을 허용하는지 여부 설정 false인 경우 해덩 컬럼에 값이 반듯이 있어야함
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "contents", nullable = false, length = 1000)
    private String contents;


    // 게시글 작성
    // Entity 클래스와 Controller 간의 데이터 전달을 위해 사용함
    // 클라이언트에서 들어오는 요청 데이터를 담고 Entity로 변환되거나 Entity에서 추출된 정보를 클라이언트에게 보낼 때 사용
    // Board Entity 클래스의 생성자를 정의함 객체가 생성괼때 초기화 작업 수행
    public Board (String username,BoardRequestDto requestDto){
        // Board Entity 객체의 username 필드를 BoardRequestDto 객체의 getUsername 메서드로 부터 얻은 값으로 초기화
        this.username = username;
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
    }

    // 게시글 수정
    // update 메서드가 Board Entity 객체를 주어진 BoardRequestDto 객체의 값으로 업데이트 하는 역활을 수행
    public void update(BoardRequestDto requestDto){

        this.contents = requestDto.getContents();

        this.title = requestDto.getTitle();
    }
}
