package com.demir.berkay.blog.service;

import org.springframework.data.domain.Page;
import com.demir.berkay.blog.model.Post;
import java.util.List;
import java.util.Optional;

public interface PostService {

    Post createPost(Post post);
    Post updatePost(Long id, Post post);
    void deletePost(Long id);
    Optional<Post> getPostById(Long id);
    Optional<Post> getPostBySlug(String slug);
    List<Post> getPostsByCategory(String categoryName);
    List<Post> getPostsByTag(String tagName);
    Page<Post> searchPosts(String keyword, int page, int size);
    Page<Post> getLatestPosts(int page, int size);
    
}
