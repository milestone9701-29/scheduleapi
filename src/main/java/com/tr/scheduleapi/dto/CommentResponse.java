package com.tr.scheduleapi.dto;

// domain - read(get)
import com.tr.scheduleapi.domain.Comment;
// @Value

import lombok.Value;

// 불변 시간
import java.time.LocalDateTime;

@Value
public class CommentResponse {

    Long id;

    String content;
    String author;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static CommentResponse from(Comment c) {
        return new CommentResponse(
                c.getId(),
                c.getContent(),
                c.getAuthor(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }
}
