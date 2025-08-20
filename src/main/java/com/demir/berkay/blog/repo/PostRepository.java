package com.demir.berkay.blog.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demir.berkay.blog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Find all posts by a specific author (user id)
    List<Post> findByAuthorId(Long authorId);

    // Find posts with title containing a keyword (case-insensitive)
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    // Find posts by category name (optional feature)
    List<Post> findByCategories_NameIgnoreCase(String categoryName);

    // Find posts by tag name (optional feature)
    List<Post> findByTags_NameIgnoreCase(String tagName);
    

    // Get latest posts (with Pageable)
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // Find by SEO slug
    Optional<Post> findBySlug(String slug);
}
