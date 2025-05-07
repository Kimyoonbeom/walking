package com.example.walking.controller;

import com.example.walking.dto.request.CommentRequestDto;
import com.example.walking.dto.response.CommentResponseDto;
import com.example.walking.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private  final CommentService commentService;

    // 댓글 생성
    @PostMapping("/schedules/{scheduleId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> create(
            @PathVariable Long scheduleId,
            @RequestBody CommentRequestDto dto
    ) {
        commentService.create(scheduleId, dto);
        return Map.of("message", "댓글이 등록되었습니다.");
    }

    // 댓글 목록 조회(페이징)
    @GetMapping("/schedules/{scheduleId}")
    public Page<CommentResponseDto> listBySchedule(
            @PathVariable Long scheduleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        return commentService.findTopCommentsBySchedule(scheduleId, pageable);
    }

    // 단일 댓글 조회
    @GetMapping("/{commentId}")
    public CommentResponseDto detail(@PathVariable Long commentId) {
        return commentService.findById(commentId);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public CommentResponseDto update(@PathVariable Long commentId, @RequestBody CommentRequestDto dto) {
        return commentService.update(commentId, dto);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }
}
