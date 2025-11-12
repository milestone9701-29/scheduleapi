package com.tr.scheduleapi.repository;

import com.tr.scheduleapi.domain.Schedule;

// page
import org.springframework.data.domain.Page;

//pageable
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Page<Schedule> findAllByAuthorOrderByUpdatedAtDesc(String author, Pageable pageable); // 찾다 모두 저자 주문 ~에 의해 갱신된 내림차순으로
    Page<Schedule> findAllByOrderByUpdatedAtDesc(Pageable pageable); // 찾다 모두 주문 ~에 의해 갱신된 내림차순으로
}
