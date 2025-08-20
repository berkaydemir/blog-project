package com.demir.berkay.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demir.berkay.blog.model.Post;
import com.demir.berkay.blog.repo.PostRepository;

@Service
public class PostServiceImpl implements PostService {
	
	private final PostRepository postRepository;
	
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, Post post) {
        return postRepository.findById(id)
                .map(existingPost -> {
                    existingPost.setTitle(post.getTitle());
                    existingPost.setContent(post.getContent());
                    existingPost.setCategories(post.getCategories());
                    existingPost.setTags(post.getTags());
                    return postRepository.save(existingPost);
                })
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Optional<Post> getPostBySlug(String slug) {
        return postRepository.findBySlug(slug);
    }

    @Override
    public List<Post> getPostsByCategory(String categoryName) {
        return postRepository.findByCategories_NameIgnoreCase(categoryName);
    }

    @Override
    public List<Post> getPostsByTag(String tagName) {
        return postRepository.findByTags_NameIgnoreCase(tagName);
    }

    @Override
    public Page<Post> searchPosts(String keyword, int page, int size) {
        return postRepository.findByTitleContainingIgnoreCase(keyword, 
                PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    @Override
    public Page<Post> getLatestPosts(int page, int size) {
        return postRepository.findAllByOrderByCreatedAtDesc(
                PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

}
