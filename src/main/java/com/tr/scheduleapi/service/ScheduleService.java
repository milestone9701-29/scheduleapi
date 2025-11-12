package com.tr.scheduleapi.service; // 연산


// domain
import com.tr.scheduleapi.domain.Schedule;

// Controller - ScheduleDeleteRequest - Controller - service.delete(id, pw) : 없어도 됨.
// Request
import com.tr.scheduleapi.dto.ScheduleCreateRequest;
import com.tr.scheduleapi.dto.ScheduleUpdateRequest;

// 403
import com.tr.scheduleapi.exception.PasswordMismatchException;

// Repository
import com.tr.scheduleapi.repository.ScheduleRepository;

// final 생성자
import lombok.RequiredArgsConstructor;

//page
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


// import java.util.regex; -> matches(), find() 전체 - 부분
// 상단 내용과 헷갈리지 않게 주의.

// Encoding PW
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// NoSuchElementException
import java.util.NoSuchElementException;

// 연산
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Schedule create(ScheduleCreateRequest req) { // Schedule -> @builder
        Schedule s = Schedule.builder()
                .title(req.getTitle()) // Read ~
                .content(req.getContent()) // Read ~
                .author(req.getAuthor()) // Read ~
                .passwordHash(passwordEncoder.encode(req.getPassword())) // Read ~
                .build();
        return repository.save(s);
    }

    public Page<Schedule> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Schedule get(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Schedule not found: " + id));
    }

    public Schedule update(Long id, ScheduleUpdateRequest req) {
        Schedule s = get(id);
        if (!passwordEncoder.matches(req.getPassword(), s.getPasswordHash())) {
            throw new PasswordMismatchException();
        }
        s.update(req.getTitle(), req.getContent(), req.getAuthor());
        return repository.save(s);
    }

    public void delete(Long id, String password) {
        Schedule s = get(id);
        if (!passwordEncoder.matches(password, s.getPasswordHash())) {
            throw new PasswordMismatchException();
        }
        repository.delete(s);
    }
}
