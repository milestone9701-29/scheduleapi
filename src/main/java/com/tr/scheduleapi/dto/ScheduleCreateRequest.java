package com.tr.scheduleapi.dto;

// 공백 제거
// Boilerplate 제거를 위한 개선 방법이 있는지? : @NotBlank
import jakarta.validation.constraints.NotBlank;

// Read : public String getTitle() { return title; } 등.
import lombok.Getter;
// @Size
import jakarta.validation.constraints.Size;


// 생성자
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor; // 모든 필드 값을 parameter로 받음.

@Getter
@NoArgsConstructor // parameter 없음
@AllArgsConstructor // parameter : String
public class ScheduleCreateRequest {
    @NotBlank @Size(max = 100)
    private String title;
    @NotBlank
    private String content;
    @NotBlank @Size(max = 50)
    private String author;
    @NotBlank
    private String password;
}
