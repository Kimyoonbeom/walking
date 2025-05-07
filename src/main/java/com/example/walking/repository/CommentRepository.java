package com.example.walking.repository;

import com.example.walking.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countByScheduleId(Long scheduleId);

    // 최상위 댓글(부모가 null) 페이징 조회
    Page<Comment> findByScheduleIdAndParentCommentIsNull(Long scheduleId, Pageable pageable);

    // 특정 부모 댓글의 대댓글(자식 댓글) 페이징 조회
    Page<Comment> findByParentCommentId(Long parentCommentId, Pageable pageable);
}
