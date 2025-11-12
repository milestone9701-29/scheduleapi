package com.tr.scheduleapi.dto;

// @NotBlank, @Size
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// @Getter : Read - 생성자
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {
    @NotBlank @Size(max = 100) private String content;
    @NotBlank @Size(max = 50) private String author;
    @NotBlank private String password;

}
