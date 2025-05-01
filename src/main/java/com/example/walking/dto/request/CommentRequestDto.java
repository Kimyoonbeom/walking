package com.example.walking.dto.request;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String content;
    private Long writerId;
    private Long parentCommentId; // 값이 null이면 "최"상위 댓글
}
