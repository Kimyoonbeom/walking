package com.example.walking.repository;

import com.example.walking.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countByScheduleId(Long scheduleId);

    // 페이징
    Page<Comment> findByScheduleIdAndParentComment(Long scheduleId, Pageable pageable);
}
