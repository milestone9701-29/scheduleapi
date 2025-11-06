package com.tr.scheduleapi.controller;

import com.tr.scheduleapi.dto.request.ScheduleCreateReq;
import com.tr.scheduleapi.dto.request.ScheduleDeleteReq;
import com.tr.scheduleapi.dto.request.ScheduleUpdateReq;
import com.tr.scheduleapi.dto.response.ScheduleDetailRes;
import com.tr.scheduleapi.dto.response.ScheduleRes;
import com.tr.scheduleapi.service.ScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api/schedules") // 진입
@RequiredArgsConstructor // 생성자 주입 : private final ScheduleService;
public class ScheduleController { // HTTP 요청 -> 메서드 실행 라우팅 분기 표지판.
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleRes> create(@Valid @RequestBody ScheduleCreateReq req){ // POST : 201 :
        ScheduleRes res = scheduleService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res); // 생성 성공 시
    }
    @GetMapping
    public ResponseEntity<List<ScheduleRes>> list(@RequestParam(required = false) String author){ // GET : 200 OK 반환
        return ResponseEntity.ok(scheduleService.list(author)); //
    }
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDetailRes> get(@PathVariable Long id){ // GET : 단건 상세 조회 : 없으면 Service에서 예외. -> GlobalExceptionHandler 매핑.
        return ResponseEntity.ok(scheduleService.getDetail(id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleRes> update(@PathVariable Long id, @Valid @RequestBody ScheduleUpdateReq req){ // 일부 필드 변경 -> 200 OK -> DTO
        return ResponseEntity.ok(scheduleService.update(id, req));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @Valid @RequestBody ScheduleDeleteReq req){ // DEL
        scheduleService.delete(id, req);
    }
}

/* 요청 1건 처리
- HTTP 메서드: GET, POST, PATCH, DELETE …
- 경로: /api/schedules or /api/schedules/10
- 쿼리스트링: ?author=kim
- 바디(JSON): { "title": "...", ... }
-> DispatcherServlet(Spring) : 가장 잘 맞는 handlerMethod 고르기
- Method : GET, 경로가 /api/schedules -> @GetMapping
- Method : GET, 경로가 /api/schedules/10 -> @GetMapping("/{id}")
- Method : PATCH, 경로가 /api/schedules/10 -> @PatchMapping("/{id}")
-> routing 기준 : HTTP 메서드, URL 패턴
- HttpStatus는 라우팅에 전혀 안 쓰임. : HttpStatus는 “이미 메서드가 실행된 후에 어떤 숫자 코드(200/201/204 등)를 응답으로 보낼까?”를 정하는 용도. */

/* 어노테이션 정리
@PathVariable : URL 경로 일부(/{id})를 자바 Parameter로 바인딩.
예시 : /api/schedules/10 -> get(@PathVariable Long id) : id의 값 10. : URL 안의 값.
@GetMapping("/{id}")
public ResponseEntity<ScheduleDetailRes> get(@PathVariable Long id) { ... } : Long id는 경로 변수에 해당.

@RequestParam : QueryParameter(?author=kim)를 자바 Parameter로 바인딩.
예시 : /api/schedules?author=kim : author : kim.
@GetMapping
public ResponseEntity<List<ScheduleRes>> list(@RequestParam(required=false) String author) { ... }
* required=false : 보냈을 때 filter, 안보낼 경우 전체 리스트와 같은 로직.

@RequestBody : 요청 Body(JSON) -> DTO(역직렬화)
예 : POST /api/schedules
{"title":"집", "content":"내일 10시", "author":"tak", "password":"..."}
-> ScheduleCreateReq req
@PostMapping
public ResponseEntity<ScheduleRes> create(
    @Valid @RequestBody ScheduleCreateReq req
) { ... }

@Valid : DTO(@RequestBody, @RequestParam)에 대한 검증규칙(JSR-380 Bean Validation) 강제.
DTO 필드에 @NotBlank, @Size(max=30) 같은 조건
-> 조건 위반 시 이 Method 본문이 호출 되기 전에 예외가 던져짐.
-> GlobalExceptionHandler가 그걸 받아서 400 Bad Request와 같은 표준 에러 JSON으로 응답.
* 컨트롤러 메서드 안에 if문으로 매번 수동 검증을 하지 않아도 된다.
* 유효하지 않은 입력은 아예 service까지 못내려가게 막음.

ResponseEntity<T> : 상태 코드, header, body 직접 구성 :
return ResponseEntity
	.status(HttpStatus) // 201
	.body(res); // 응답.

@ResponseStatus : 이 메서드는 항상 X 코드로 나와라.
예시
@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void delete(...) { ... }
-> 성공 시 204 No Content, 바디 없음.
* 삭제 API 예시.

스케쥴 구현 시, 진입 - 엔드포인트까지 도달 -> 다른 클래스로 분기가 흩어진다가 기본 골자.
*/
