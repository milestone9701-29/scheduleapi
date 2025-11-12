package com.tr.scheduleapi.dto;

import jakarta.validation.constraints.NotBlank;

// Read
import lombok.Getter;

// 생성자
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


// Read
@Getter
// 생성자
@NoArgsConstructor
@AllArgsConstructor
// 입력 시 필요 조건 : PW
public class ScheduleDeleteRequest {
    @NotBlank
    private String password;
}
