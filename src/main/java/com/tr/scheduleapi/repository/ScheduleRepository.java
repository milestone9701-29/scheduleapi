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
    Page<Schedule> findAllByAuthorOrderByUpdatedAtDesc(String author, Pageable pageable);
    Page<Schedule> findAllByOrderByUpdatedAtDesc(Pageable pageable);
}
// SELECT * FROM schedules WHERE author=? ORDER BY updated_at DESC;
// ORDER BY updated_at DESC