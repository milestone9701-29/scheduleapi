package com.tr.scheduleapi.dto;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Read, final, 모든 매개변수 허용 생성자
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateRequest { // 전달 -> 검증 : GlobalExceptionHandler
    // 아무것도 입력하지 않은 경우 : 제약 수정 필요.
    @Size(max = 30)
    private String title;
   // private String content; : 제목, 작성자만 수정.
    @Size(max = 50)
    private String author;
    @NotBlank
    private String password;
}
