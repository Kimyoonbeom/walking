package com.example.walking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer commentCount;
    private List<CommentResponseDto> comments;
}
