package com.tr.scheduleapi.domain; // Entity, Value Obj, : 도메인 규칙

import jakarta.persistence.*; // JPA MAPPING : Entity, Id, Table, Column, GeneratedValue : Class, field - Table, Column : 객체 - DB
// * 분리 필요 : infra 등.
import lombok.*; // boilerplate 제거 : 컴파일 할 때 애너테이션 프로세서 -> 자동 생성 : IDE 설정, Lombok 의존 증가.
import org.springframework.data.annotation.CreatedDate; // 최초 생성일자
import org.springframework.data.annotation.LastModifiedDate; // 최근 수정일자
import org.springframework.data.jpa.domain.support.AuditingEntityListener; // Listener : 타임 스템프 채우기
import com.fasterxml.jackson.annotation.JsonProperty; // 직렬화 제어(응답 차단)

import java.time.LocalDateTime; // 날짜, 시간 타입
// 기타 : OffsetDateTime Instant : time zone 변환

@Entity // 영속성 Entity
@Table(name="schedules") // Table Name
@EntityListeners(AuditingEntityListener.class) // @CreatedDate @LastModifiedDate @CreatedBy @LastModifiedBy : @EnableJpaAuditing 필요.
@Getter// Read
@NoArgsConstructor(access=AccessLevel.PROTECTED) // JPA용 기본 생성자만 노출.
public class Schedule{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) // PK, DB auto-increment.
    private Long id;
    @Column(nullable=false, length=30) // 30
    private String title;
    @Column(nullable=false, length=200) // 200
    private String content;
    @Column(nullable=false, length=30) // 30
    private String author;
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY) // 응답 차단.
    @Column(nullable=false, length=60)  // Not Null, varchar 길이 지정
    private String password;
    @CreatedDate // 최초 생성일은 변경할 필요가 없으니 updatable을 false로.
    @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;
    @LastModifiedDate // 자동갱신
    @Column(nullable=false)
    private LocalDateTime modifiedAt;

    @Builder // 생성 시 필요한 Field만 받도록 제한. : id - 감사 field 제외.
    private Schedule(String title, String content, String author, String encodedPassword){
        if(title==null || title.isBlank() || title.length() > 30) throw new IllegalArgumentException("[ Invalid Title ! ]");
        if(content==null || content.isBlank() || content.length() > 200) throw new IllegalArgumentException("[ Invalid Content ! ]");
        if(author==null || author.isBlank() || author.length() > 30) throw new IllegalArgumentException("[ Invalid Author ! ]");
        this.title=title;
        this.content=content;
        this.author=author;
        this.password=encodedPassword;
    }
    // 의도 있는 변경 메서드.
    public void changeTitleAndContent(String title, String content){
        this.title=title;
        this.content=content;
    }
    // Encoded PW : 해시 처리 후 호출.
    public void applyEncodedPassword(String encodedPassword){
        this.password=encodedPassword;
    }
}

// 직접 구현 시 코드 길이가 방대해지는 요소를 제거하기 위함. : Getter Setter - R W 기능, 최초 생성일자 - 최근 수정일자.
// @Getter @Setter @NoArgsConstructor(기본 생성자) @AllArgsConstructor(모든 필드 생성자) @Builder(빌더 패턴)
// -> 필요한 부분만 Write 구현 : title, content :  세터 최소화, 비밀번호 처리 경로 분리.