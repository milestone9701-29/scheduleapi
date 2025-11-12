package com.tr.scheduleapi.dto;

// domain
import com.tr.scheduleapi.domain.Comment;
import com.tr.scheduleapi.domain.Schedule;

//@Value
import lombok.Value;

// createdAt - updateAt
import java.time.LocalDateTime;

// List
import java.util.List;

@Value
public class ScheduleDetailResponse {
    Long id;
    String title;
    String content;
    String author;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    List<CommentResponse> comments;

    public static ScheduleDetailResponse of(Schedule s, List<Comment> cs){ // Comment Response
        return new ScheduleDetailResponse(
                s.getId(),
                s.getTitle(),
                s.getContent(),
                s.getAuthor(),
                s.getCreatedAt(),
                s.getUpdatedAt(),
                cs.stream().map(CommentResponse::from).toList() // 별도 조회 기능.
        );
    }
}
