package com.tr.scheduleapi.service;

// domain
import com.tr.scheduleapi.domain.Comment;
import com.tr.scheduleapi.domain.Schedule;

// Input : Create
import com.tr.scheduleapi.dto.CommentCreateRequest;

// Repository
import com.tr.scheduleapi.exception.CommentLimitExceeded;
import com.tr.scheduleapi.repository.ScheduleRepository;
import com.tr.scheduleapi.repository.CommentRepository;

// Transactional Write
import org.springframework.transaction.annotation.Transactional;

// PW Encode
import org.springframework.security.crypto.password.PasswordEncoder;

// 생성자 final
import lombok.RequiredArgsConstructor;

// @Service
import org.springframework.stereotype.Service;


import java.util.List;

// NoSuchElementException.
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
        public Comment create(Long scheduleId, CommentCreateRequest req) {
            Schedule s = scheduleRepository.findById(scheduleId) // findById or ElseThrow()
                    .orElseThrow(() -> new NoSuchElementException("Schedule not found: " + scheduleId));
            if (commentRepository.countBySchedule_Id(scheduleId) >= 10) {
                throw new CommentLimitExceeded();
            }
            Comment c = Comment.builder()
                .schedule(s)
                .content(req.getContent())
                .author(req.getAuthor())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .build();
                return commentRepository.save(c);
    }

    @Transactional(readOnly=true) //
    public List<Comment> listBySchedule(Long scheduleId) {
        return commentRepository.findAllBySchedule_IdOrderByCreatedAtAsc(scheduleId);
    }
}