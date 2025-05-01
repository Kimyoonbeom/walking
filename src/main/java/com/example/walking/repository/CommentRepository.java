package com.example.walking.repository;

import com.example.walking.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByParentCommentId(Long parentCommentId);
}
