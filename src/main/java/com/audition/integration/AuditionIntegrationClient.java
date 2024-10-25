package com.audition.integration;

import com.audition.common.constants.ApiConstants;
import com.audition.common.exception.PostNotFoundException;
import com.audition.dto.AuditionPostDTO;
import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuditionIntegrationClient {

    @Autowired
    private RestTemplate restTemplate;

    public Optional<List<AuditionPost>> getPosts() {
        final List<AuditionPost> auditionPostsList = restTemplate.getForObject(ApiConstants.POST_ENDPOINT, List.class);
        return Optional.ofNullable(auditionPostsList);
    }

    public Optional<AuditionPostDTO> getPostById(final int postId) {
        try {
            final AuditionPostDTO postById = restTemplate.getForObject(ApiConstants.POST_BY_ID_ENDPOINT
                .replace("{postId}", String.valueOf(postId)), AuditionPostDTO.class);
            return Optional.ofNullable(postById);
        } catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e, postId);
            return Optional.empty();
        }
    }

    public Optional<AuditionPost> getPostWithComments(final int postId) {
        final String postUrl = ApiConstants.POST_BY_ID_WITH_COMMENTS_ENDPOINT.replace("{postId}", String.valueOf(postId));
        try {
            final AuditionPost post = restTemplate.getForObject(postUrl, AuditionPost.class);
            if (post == null) {
                throw new PostNotFoundException("Post not found with ID: " + postId);
            }
            final List<Comment> comments = restTemplate.getForObject(
                String.format("https://jsonplaceholder.typicode.com/posts/%d/comments", postId), List.class);
            if (comments != null) {
                post.setComments(comments);
            } else {
                post.setComments(new ArrayList<>());
            }
            return Optional.of(post);
        } catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e, postId);
            return Optional.empty();
        }
    }

    /*public Optional<AuditionPost> getPostWithComments(final int postId) {
        final String postUrl = ApiConstants.POST_BY_ID_WITH_COMMENTS_ENDPOINT.replace("{postId}", String.valueOf(postId));
        try {
            final AuditionPost post = restTemplate.getForObject(postUrl, AuditionPost.class);
            final Optional<AuditionPost> optionalPost = Optional.ofNullable(post);
            if (optionalPost.isEmpty()) {
                throw new PostNotFoundException("Post not found with ID: " + postId);
            }
            final List<Comment> comments = restTemplate.getForObject(String.format("https://jsonplaceholder.typicode.com/posts/%d/comments", postId), List.class);
            optionalPost.get().setComments(comments);
            return optionalPost;
        } catch (HttpClientErrorException e) {
                handleHttpClientErrorException(e, postId);
                return Optional.empty(); // This line will never be reached because handleHttpClientErrorException will throw an exception
            }
        }*/

    public Optional<List<Comment>> getCommentsForPost(final int postId) {
        final String url = String.format("https://jsonplaceholder.typicode.com/comments?postId=%d", postId);
        try {
            final List<Comment> commentList = restTemplate.getForObject(url, List.class);
            return Optional.ofNullable(commentList);
        } catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e, postId);
            return Optional.empty();
        }
    }

    private void handleHttpClientErrorException(final HttpClientErrorException e, final int postId) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new PostNotFoundException("Post not found with ID: " + postId, e);
        }
    }
}