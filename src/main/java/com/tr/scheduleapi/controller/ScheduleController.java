package com.tr.scheduleapi.controller; // 분기

import com.tr.scheduleapi.domain.Schedule;

// IO DTO : DTO 정리 필요.
import com.tr.scheduleapi.dto.*;

// service
import com.tr.scheduleapi.service.CommentService;
import com.tr.scheduleapi.service.ScheduleService;

// @Valid
import jakarta.validation.Valid;

// final
import lombok.RequiredArgsConstructor;

// page
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// Sort
import org.springframework.data.domain.Sort;
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

    private final ScheduleService scheduleService;
    private final CommentService commentService; // 분리 고민 중. 분리 해야할거 같다

    @PostMapping // Create resource : 201
    public ResponseEntity<ScheduleResponse> create(@Valid @RequestBody ScheduleCreateRequest req) { // DTO -> @NotBlank
        Schedule saved = scheduleService.create(req); // -> service
        return ResponseEntity.status(HttpStatus.CREATED).body(ScheduleResponse.from(saved)); // -> dto.response
    }

    @PostMapping("/{id}/comments") // 분리분리분리분리
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long id, @Valid @RequestBody CommentCreateRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CommentResponse.from(commentService.create(id,req)));
    }

    @GetMapping // list : 컬렉션(page, 정렬)
    public ResponseEntity<Page<ScheduleResponse>> list(@PageableDefault(size = 10, sort = {"updatedAt"}, direction= Sort.Direction.DESC)
                                                           Pageable pageable, @RequestParam(required=false) String author) { // 기본 페이지 크기 10, updatedAt 정렬
        return ResponseEntity.ok(scheduleService.list(author, pageable).map(ScheduleResponse::from));
    }

    @GetMapping("/{id}") // 단건 조회
    public ResponseEntity<ScheduleDetailResponse> get(@PathVariable Long id) { // id 일치 -> 경로 변수 바인딩.
        return ResponseEntity.ok(ScheduleDetailResponse.of(scheduleService.get(id), commentService.listBySchedule(id)));
    }
    // 너무 길어요 근데 변수에 경로 넣으려니까 import 꼬여서 에러 대입하니까 에러 내뿜네
    // 전반적으로 코드 정리 테크닉을 유형별로 나눠놔야할 듯? A. 겹치는 literal 명칭 정한 상수로 꺼내거나 B. 변수에 집어넣거나 C. 영문 모를 Annotation으로 보일러 플레이트 X 등등.

    @PatchMapping("/{id}") // 수정
    public ResponseEntity<ScheduleResponse> update(@PathVariable Long id, @Valid @RequestBody ScheduleUpdateRequest req) {
        Schedule updated = scheduleService.update(id, req);
        return ResponseEntity.ok(ScheduleResponse.from(updated));
    }

    @DeleteMapping("/{id}") // 삭제
    public ResponseEntity<Void> delete(@PathVariable Long id, @Valid @RequestBody ScheduleDeleteRequest req) {
        scheduleService.delete(id, req.getPassword());
        return ResponseEntity.noContent().build();
    }
}

/* 어느정도 다 끝났으니 돌이켜보면서 적는 주석
개인적으로 과제 진입장벽이 크다 느낀 원인은, 전체 구조가 너무 추상적으로 받아들여진다. 이게 좀 컸던 것 같음.
- 3Layered Architecture, MVC 등 무엇을 중점적으로 보냐에 따라 달라지는 구조 해석.
- 추상적인 구조를 구체적인 결과물로 연결하기가 어려움.
- 따라서, 어떤 경위로 코드가 설계되었는지 또한 각각의 클래스 파일을 처음 봤을 땐 도저히 모르겠음.
- 전체 구조를 모르니, 지엽적인 정보가 어떤 목차에 해당하는지, 왜 쓰이는지 알기가 어려운 구조 : 정보를 구조화하기가 어렵다 : 개념 파편화.
- 수정할 사안이 생긴다면 추가적으로 무엇을 수정해야 하는지 로직 쫓기.
- 하이라이트가 의존 방향이었던 것 같음. 종 - 횡에 따른 이해와 거기에 따른 Mapping으로 분기 나누기(GlobalExceptionException). 실제 연산(Service).
쿼리 메서드로 DB와 상호작용(Repository) 등.
- 내가 지금 뭐라 말하는지도 모르겠음 ㅋㅋ

그래도 학습 방식에 대해서 다시 정리도 해보고 이거저거 정보도 좀 찾아서 보완한 계기가 되었기도 해서, 재밌는 프로젝트였음.

사실 컨트롤러 분리, DTO 패키지 분류 및 정리, 쿼리 메서드, 개인 프로젝트 시 content 관련 주석 처리, recode class로 커멘드 추가
entity 내부검증 normalizeTitle()  Author() @org.jspecify.annotations.Nullable String title
Objects.requireNonNull(T obj, String msg) 와의 차이 등.
아직 학습할건 많다는걸 느낌. 하다보면 되겠지요.
*/
