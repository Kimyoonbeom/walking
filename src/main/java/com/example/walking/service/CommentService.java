package com.example.walking.service;

import com.example.walking.dto.request.CommentRequestDto;
import com.example.walking.dto.response.CommentResponseDto;
import com.example.walking.entity.Comment;
import com.example.walking.entity.Schedule;
import com.example.walking.repository.CommentRepository;
import com.example.walking.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    //댓글 생성
    public CommentResponseDto create(Long scheduleId, CommentRequestDto dto){
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
        Comment parentComment = null;

        if (dto.getParentCommentId() != null) {
            parentComment = commentRepository.findById(dto.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("최상위 댓글이 존재하지 않습니다."));

            // 1 Depth만 허용
            if (parentComment.getParentComment() != null) {
                throw new RuntimeException("대댓글에는 답글을 달 수 없습니다.");
            }
        }
        Comment comment = new Comment(dto.getContent(), dto.getWriterId(), schedule, parentComment);
        Comment saved = commentRepository.save(comment);
        return toResponse(saved, new ArrayList<>());
    }
    // 최상위 댓글(부모가 없는 댓글) 페이징 조회
    public Page<CommentResponseDto> findTopCommentsBySchedule(Long scheduleId, Pageable pageable) {
        Page<Comment> rootComments = commentRepository.findByScheduleIdAndParentCommentIsNull(scheduleId, pageable);
        List<CommentResponseDto> content = new ArrayList<>();
        for (Comment comment : rootComments.getContent()) {
            content.add(toResponse(comment, buildChildren(comment)));
        }
        return new PageImpl<>(content, pageable, rootComments.getTotalElements());
    }

    // 대댓글 리스트 생성하기
    private List<CommentResponseDto> buildChildren(Comment comment) {
        List<CommentResponseDto> childrenList = new ArrayList<>();
        for (Comment child: comment.getChildren()){
            childrenList.add(toResponse(child, buildChildren(child)));
        }
        return childrenList;
    }

    //댓글 단일 조회
    public CommentResponseDto findById(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
        return toResponse(comment, buildChildren(comment));
    }

    //댓글 수정
    public CommentResponseDto update(Long commentId, CommentRequestDto dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
        comment.update(dto.getContent());
        Comment updated = commentRepository.save(comment);
        return toResponse(updated, buildChildren(updated));
    }

    //댓글 삭제
    public void delete(Long commentId){
        commentRepository.deleteById(commentId);
    }

    // 엔티티 → DTO 변환
    private CommentResponseDto toResponse(Comment comment, List<CommentResponseDto> children) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .writerId(comment.getWriterId())
                .scheduleId(comment.getSchedule().getId())
                .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .children(children)
                .build();
    }
}
