package com.BlogApplaction.backend.repository;

import com.BlogApplaction.backend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
