package com.demir.berkay.blog.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.demir.berkay.blog.model.Post;
import com.demir.berkay.blog.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Paginated latest posts
    @GetMapping
    public Page<Post> getPosts(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return postService.getLatestPosts(page, size);
    }

    // GET single post by id -> returns 200 or 404
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create post -> returns 201 Created with Location header
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, UriComponentsBuilder uriBuilder) {
        Post created = postService.createPost(post);
        return ResponseEntity
                .created(uriBuilder.path("/api/posts/{id}").buildAndExpand(created.getId()).toUri())
                .body(created);
    }

    // Update post -> 200 OK if exists, 404 if not
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        return postService.getPostById(id)
                .map(existing -> ResponseEntity.ok(postService.updatePost(id, post)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete -> 204 No Content if deleted, 404 if not found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(existing -> {
                    postService.deletePost(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
