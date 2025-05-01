package com.example.walking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long writerId;
    private Long scheduleId;
    private Long parentCommentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponseDto> children;
}
