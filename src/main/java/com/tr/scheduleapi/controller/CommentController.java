package com.tr.scheduleapi.controller;

import com.tr.scheduleapi.dto.request.CommentCreateReq;
import com.tr.scheduleapi.dto.request.CommentUpdateReq;
import com.tr.scheduleapi.dto.request.CommentDeleteReq;

import com.tr.scheduleapi.dto.response.CommentRes;
import com.tr.scheduleapi.service.CommentService;

import jakarta.validation.Valid; // 검증
import lombok.RequiredArgsConstructor; // 생성자
import org.springframework.http.HttpStatus; // HTTP 코드
import org.springframework.http.ResponseEntity; // 응답
import org.springframework.web.bind.annotation.*; // 바인딩.



// HTTP 메서드+URL 패턴 분기

@RestController // 바인딩
@RequestMapping("/api/schedules/{scheduleId}/comments") // 경로 - 분기
@RequiredArgsConstructor
public class CommentController{
    private final CommentService commentService;

    @PostMapping // POST : 생성
    public ResponseEntity<CommentRes> create(@PathVariable Long scheduleId, @Valid @RequestBody CommentCreateReq req){
        CommentRes res=commentService.create(scheduleId, req);
        return ResponseEntity.created(URI.create("/api/schedules/" + res.id())).body(res); // 201 + Location Header
    }
    @GetMapping // GET : 조회
    public ResponseEntity<List<CommentRes> list(@PathVariable Long scheduleId, @PageableDefault(size=10, sort="createdAt", direction=Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.ok(commentService.list(scheduleId, pageable)); // 200
    }
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentRes> update(@PathVariable Long commentId, @Valid @RequestBody CommentUpdateReq req){
        return ResponseEntity.ok(commentService.update(commentId, req)); // 200
    }
    @DeleteMapping("/{commentId}") // DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long commentId, @Valid @RequestBody CommentDeleteReq req){
        commentService.delete(commentId, req); // 204
    }


}