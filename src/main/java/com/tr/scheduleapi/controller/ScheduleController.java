package com.tr.scheduleapi.controller; // 분기

import com.tr.scheduleapi.domain.Schedule;

// Input DTO
import com.tr.scheduleapi.dto.ScheduleCreateRequest;
import com.tr.scheduleapi.dto.ScheduleDeleteRequest;
import com.tr.scheduleapi.dto.ScheduleUpdateRequest;

// Output DTO
import com.tr.scheduleapi.dto.ScheduleResponse;

// service
import com.tr.scheduleapi.service.ScheduleService;

// @Valid
import jakarta.validation.Valid;

// final
import lombok.RequiredArgsConstructor;

// page
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

// Output : ResponseEntity 등.
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Mapping, @RequestBody, @PathVariable
import org.springframework.web.bind.annotation.*;


// ResponseBody : Data 반환
// Controller : View 반환
// * View 렌더링 시 ViewResolver 사용
// RestController : Data + View : JSON 형태. 반환 값이 Body여야 함.
@RestController
@RequestMapping("/api/schedules") // Input, start.
@RequiredArgsConstructor // final 생성자
public class ScheduleController {

    private final ScheduleService service;

    @PostMapping // Create resource : 201
    public ResponseEntity<ScheduleResponse> create(@Valid @RequestBody ScheduleCreateRequest req) { // DTO -> @NotBlank
        Schedule saved = service.create(req); // -> service
        return ResponseEntity.status(HttpStatus.CREATED).body(ScheduleResponse.from(saved)); // -> dto.response
    }

    @GetMapping // list : 컬렉션(page, 정렬)
    public ResponseEntity<Page<ScheduleResponse>> list(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable) { // 기본 페이지 크기 10, id 정렬
        Page<ScheduleResponse> page = service.list(pageable).map(ScheduleResponse::from);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}") // 단건 조회
    public ResponseEntity<ScheduleResponse> get(@PathVariable Long id) { // id 일치 -> 경로 변수 바인딩.
        return ResponseEntity.ok(ScheduleResponse.from(service.get(id)));
    }

    @PatchMapping("/{id}") // 수정
    public ResponseEntity<ScheduleResponse> update(@PathVariable Long id, @Valid @RequestBody ScheduleUpdateRequest req) {
        Schedule updated = service.update(id, req);
        return ResponseEntity.ok(ScheduleResponse.from(updated));
    }

    @DeleteMapping("/{id}") // 삭제
    public ResponseEntity<Void> delete(@PathVariable Long id, @Valid @RequestBody ScheduleDeleteRequest req) {
        service.delete(id, req.getPassword());
        return ResponseEntity.noContent().build();
    }
}
