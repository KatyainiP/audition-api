package com.audition.service;

import com.audition.common.exception.PostNotFoundException;
import com.audition.dto.AuditionPostDTO;
import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditionService {

    @Autowired
    private AuditionIntegrationClient auditionIntegrationClient;


    public Optional<List<AuditionPost>> getPosts(final String filter) {
        return responseFilterLogic(filter);
    }

    public Optional<AuditionPostDTO> getPostById(final int postId) {
        return auditionIntegrationClient.getPostById(postId);

    }

    public Optional<AuditionPost> getPostWithComments(final int postId) {
        return postIdValidation(postId);
    }

    public Optional<List<Comment>> getCommentsForPost(final int postId) {
        return auditionIntegrationClient.getCommentsForPost(postId);
    }

    //The responseFilterLogic method takes a filter string and returns a filtered list based on whether the Title or Body of each post
    // contains the filter string (case-insensitive)
    private Optional<List<AuditionPost>> responseFilterLogic(final String filter) {
        final Optional<List<AuditionPost>> auditionPostList = auditionIntegrationClient.getPosts();
        final List<AuditionPost> auditionPostListFiltered = auditionPostList.map(posts -> posts.stream()
                .filter(post -> Optional.ofNullable(post.getTitle())
                    .map(title -> title.toLowerCase(Locale.getDefault()).contains(filter.toLowerCase(Locale.getDefault())))
                    .orElse(false))
                .collect(Collectors.toList()))
            .orElseGet(List::of);
        return Optional.ofNullable(auditionPostListFiltered);
    }

    private Optional<AuditionPost> postIdValidation(final int postId) {
        Optional.of(postId)
            .filter(id -> id > 0)
            .orElseThrow(() -> new IllegalArgumentException("Post ID must be a positive integer."));
        return Optional.ofNullable(auditionIntegrationClient.getPostWithComments(postId))
            .orElseThrow(() -> new PostNotFoundException("No post found with ID: " + postId));
    }
}
