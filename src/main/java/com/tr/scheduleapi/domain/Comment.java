package com.tr.scheduleapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="comments",
        indexes={
                @Index(name="idx_comments_schedule", columnList="schedule_id"),
                @Index(name="idx_comments_created", columnList="created_at")
        })
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Comment extends BaseTimeEntity{ // BaseTime : Comment, Schedule : @MappedSuperClass
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length=100) // 요구 조건 100자
    private String content;
    @Column(nullable=false, length=50)
    private String author;
    @Column(name="password_hash", nullable=false, length=100)
    private String passwordHash;
    
    // 기본 접근 방식 : 단방향 -> 1
    // default fetch=EAGER
    @ManyToOne(fetch=FetchType.LAZY, optional=false) 
    @JoinColumn(name="schedule_id")
    private Schedule schedule;
}