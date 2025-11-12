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

    public Page<Schedule> list(String author, Pageable pageable) {
        if(author!=null&&!author.isBlank()){ // null + 공백 : isEmpty() 이건 안되려나?
            return repository.findAllByAuthorOrderByUpdatedAtDesc(author, pageable); // 찾다 모두 저자 주문 ~에의해 업데이트 된 내림차순으로
        }
        return repository.findAllByOrderByUpdatedAtDesc(pageable); // 찾다 모두 주문 ~에의해 갱신된 내림차순으로
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
        if(req.getTitle() == null && req.getAuthor() == null){ throw new IllegalArgumentException("수정할 필드가 없습니다."); }

        s.update(req.getTitle(), req.getAuthor()); // req.getContent(), Title - Author
        return repository.save(s); // Auditing -> 자동갱신.
    }

    public void delete(Long id, String password) {
        Schedule s = get(id);
        if (!passwordEncoder.matches(password, s.getPasswordHash())) {
            throw new PasswordMismatchException();
        }
        repository.delete(s);
    }


}
