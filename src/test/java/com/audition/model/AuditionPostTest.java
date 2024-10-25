package com.audition.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class AuditionPostTest {

    private AuditionPost auditionPost;
    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = new Comment();
        comment.setPostId(1);
        comment.setId(1);
        comment.setName("Sample Name");
        comment.setEmail("sample@example.com");
        comment.setBody("This is a sample comment.");
        final List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);

        auditionPost = new AuditionPost();
        auditionPost.setPostId(1);
        auditionPost.setBody("This is a sample Post.");
        auditionPost.setTitle("Title");
        auditionPost.setUserId(1234);
        auditionPost.setComments(commentList);
    }

    @Test
    void testSettersAndGetters() {
        assertEquals(1234, auditionPost.getUserId());
        assertEquals(1, auditionPost.getPostId());
        assertEquals("Title", auditionPost.getTitle());
        assertEquals("This is a sample Post.", auditionPost.getBody());
        assertEquals(1, auditionPost.getComments().size());
        assertEquals(comment, auditionPost.getComments().get(0));
    }

    @Test
    void testGetCommentsReturnsCopy() {
        final Comment comment = new Comment();
        final AuditionPost post = new AuditionPost(1, 1, "Test Title", "Test Body", Collections.singletonList(comment));

        final List<Comment> comments = post.getComments();
        comments.clear();

        assertEquals(1, post.getComments().size());
    }

    @Test
    void testSetCommentsCreatesNewList() {
        final AuditionPost post = new AuditionPost();
        final Comment comment = new Comment();

        post.setComments(Collections.singletonList(comment));
        post.getComments().clear();
        assertEquals(1, post.getComments().size());
    }
}