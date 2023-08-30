package com.sparta.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
// JPA Entity 클래스들이 해당 추상 클래스를 상속할 경우 createdAt, modifiedAt 처럼
// 추상 클래스에 선언한 멤버변수를 컬럼으로 인식할 수 있습니다.
@MappedSuperclass
//해당 클래스에 Auditing 기능을 포함시켜 줍니다.
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {
    // Entity 객체가 생성되어 저장될 때 시간이 자동으로 저장됩니다.
    @CreatedDate
    // 업데이트가 되지않게 막아줌
    // 최초 생성시간만 저장됨
    @Column(updatable = false)
    //날짜 타입(java.util.Date, java.util.Calendar)을 매핑할 때 사용합니다.
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate //조회한 Entity 객체의 값을 변경할 때 변경된 시간이 자동으로 저장됩니다.
    @Column
    @Temporal(TemporalType.TIMESTAMP)
//    *DATE : ex) 2023-01-01
//    *TIME : ex) 20:21:14
//    *TIMESTAMP : ex) 2023-01-01 20:22:38.771000
    private LocalDateTime modifiedAt;
}