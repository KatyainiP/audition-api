package com.audition.web;

import com.audition.common.exception.PostNotFoundException;
import com.audition.dto.AuditionPostDTO;
import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import com.audition.service.AuditionService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditionController {

    @Autowired
    AuditionService auditionService;

    @GetMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<AuditionPost>> getPosts(@RequestParam final String filter) {
        try {
            final Optional<List<AuditionPost>> optionalPost = auditionService.getPosts(filter);
            return optionalPost
                .map(post -> ResponseEntity.ok(post))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null));
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping(value = "/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<AuditionPostDTO> getPostById(@PathVariable("id") final int postId) {
        try {
            final Optional<AuditionPostDTO> optionalPost = auditionService.getPostById(postId);
            return optionalPost
                .map(post -> ResponseEntity.ok(post))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null));
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<AuditionPost> getPostWithComments(@PathVariable final int postId) {
        try {
            final Optional<AuditionPost> optionalPost = auditionService.getPostWithComments(postId);
            return optionalPost
                .map(post -> ResponseEntity.ok(post))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null));
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<Optional<List<Comment>>> getCommentsForPost(@PathVariable("id") final int postId) {
        try {
            final Optional<List<Comment>> comments = auditionService.getCommentsForPost(postId);
            if (comments == null || comments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
            }
            return ResponseEntity.ok(comments);
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
