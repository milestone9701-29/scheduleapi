package com.tr.scheduleapi.domain;

import jakarta.persistence.*;// JPA MAPPING : Entity, Id, Table, Column, GeneratedValue : Class, field - Table, Column : 객체 - DB
import lombok.*;
import org.springframework.data.annotation.CreatedDate;  // 최초 생성일자
import org.springframework.data.annotation.LastModifiedDate; // 최근 수정일자
import org.springframework.data.jpa.domain.support.AuditingEntityListener; // Listener : 타임 스템프 채우기
import com.fasterxml.jackson.annotation.JsonProperty; // 직렬화 제어(응답 차단)

import java.time.LocalDateTime; // 날짜, 시간 타입

@Entity
@Table(name="comments") // Table Name
@EntityListeners(AuditingEntityListener.class) // 감사
@Getter // read
@NoArgsConstructor(access=AccessLevel.PROTECTED) // JPA용 기본 생성자
public class Comment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) // PK
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) // N:1 기본 EAGER. N+1 방지.
    @JoinColumn(name="schedule_id", nullable=false) // FK comments.schedule_id -> schedules.id
    private Schedule schedule;
    @Column(nullable=false, length=100)
    private String content;
    @Column(nullable=false, length=30)
    private String author;
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Column(nullble=false, length=60) // 수정 예정.
    private String password;
    @CreatedDate // 최초 생성 - EntityListeners
    @Column(nullable=false, updatable=false) // 최초 생성 -> update 갱신 X
    private LocalDateTime createdAt; // 불변값
    @LastModifiedDate
    @Column(nullable=false)
    private LocalDateTime modifiedAt;

    @Builder // 필요한 Field만 받기 : id, 감사 필드 제외.
    private Comment(Schedule schedule, String content, String author, String encodedPassword){
        if(schedule==null) throw new IllegalArgumentException("[ Invalid Schedule ! ]");
        if(content==null || content.isBlank() || content.length() > 100) throw new IllegalArgumentException("[ Invalid Title ! ]");
        if(author==null || author.isBlank() || author.length() > 30) throw new IllegalArgumentException("[ Invalid Author ! ]");
        if(encodedPassword==null || encodedPassword.isBlank() || encodedPassword.length()>60) throw new IllegalArgumentException("[ Invalid PW ! ]");
        this.content=content;
        this.author=author;
        this.password=encodedPassword;
    }
    // 변경 메서드
    public void changeContent(String content){
        if(content==null || content.isBlank() || content.length() > 100) throw new IllegalArgumentException("[ Invalid Title ! ]");
        this.content=content;
    }
    // Encoded PW : 해시 처리 후 호출.
    public void applyEncodedPassword(String encodedPassword){
        if(encodedPassword==null || encodedPassword.isBlank() || encodedPassword.length()>60) throw new IllegalArgumentException("[ Invalid PW ! ]");
        this.password=encodedPassword;
    }
}
