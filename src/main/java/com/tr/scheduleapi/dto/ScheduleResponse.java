package com.tr.scheduleapi.dto; // Controller <- Response : Output

import com.tr.scheduleapi.domain.Schedule;

// @Value -> extends java.lang.annotation.Annotation;
import lombok.Value;

// 불변 시간 값 제공.
import java.time.LocalDateTime;

@Value //
public class ScheduleResponse {
    Long id;

    String title;
    String content;
    String author;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static ScheduleResponse from(Schedule s) {
        return new ScheduleResponse(
                s.getId(),
                s.getTitle(),
                s.getContent(),
                s.getAuthor(),
                s.getCreatedAt(),
                s.getUpdatedAt() // getModifiedAt 등..
        );
    }
}
// 응답에 pw 넣지 말것.