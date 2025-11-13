package com.tr.scheduleapi.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity

@Table(
        name = "schedules", // table name
        indexes=@Index( // DDL : Index 정의
                name="idx_author_updated", // index name
                columnList="author, updated_at")) // 컬럼명 : Entity와 헷갈리지 않도록 주의.
// Hibernate ddl-auto : update 관련 점검 시
// SQL CLI : CREATE INDEX idx_author_updated ON schedules (author, updated_at);
// 적용
// SHOW INDEX FROM schedules;
/*
EXPLAIN
SELECT *
FROM schedules
WHERE author='X'
ORDER BY updated_at DESC;
 */

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

    // String content << 과제 요구 사항. X
    public void update(String title, String author) { // 패치용 Write
        if (title != null) this.title = title;
        // if (content != null) this.content = content; 제목, 작성자만 수정.
        if (author != null) this.author = author;
    }
}
