package com.tr.scheduleapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity

@Table(name = "schedules") // 테이블명

//Read
@Getter
// 접근제어자 설정.
// 지연 로딩 : proxy 객체 -> 조회.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Schedule extends BaseTimeEntity { // 매핑
    // GenerationType.SEQUENCE : 대량 삽입 작업 시 : 해당 내용 보충 필요.

    // MySQL - MariaDB : AUTO_INCREMENT
    // INSERT SQL이 실행
    // -> JPA는 DB가 생성한 ID 값을 들고옴.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK : 자동 생성
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 50)
    private String author;

    // 해시 저장, API로 반환 X
    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    public void update(String title, String content, String author) { // 동작이 의도된 메서드
        if (title != null) this.title = title;
        if (content != null) this.content = content;
        if (author != null) this.author = author;
    }
}
