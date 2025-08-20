package com.demir.berkay.blog.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demir.berkay.blog.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Get comments for a post
    List<Comment> findByPost_Id(Long postId);

    // Get comments by author
    List<Comment> findByAuthor_Id(Long authorId);
}
