package com.tr.scheduleapi.repository;

// domain
import com.tr.scheduleapi.domain.Comment;

// @Repository
import org.springframework.stereotype.Repository;

// extends JpaRepository
import org.springframework.data.jpa.repository.JpaRepository;

// List
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countBySchedule_Id(Long scheduleId); // 모든 row 순회
    // boolean existsBySchedule_Id(Long scheduleId); // 조건을 만족하는 데이터 존재 시 종료
    List<Comment> findAllBySchedule_IdOrderByCreatedAtAsc(Long scheduleId);
    // SELECT * FROM comments WHERE schedule_id=? ORDER BY created_at asc : 오름차순.

    // 보충 사항 : Hibernate 로그
}