package com.mini.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mini.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
