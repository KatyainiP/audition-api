package com.audition.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class CommentTest {

    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = new Comment();
        comment.setPostId(1);
        comment.setId(1);
        comment.setName("Sample Name");
        comment.setEmail("sample@example.com");
        comment.setBody("This is a sample comment.");
    }

    @Test
    void testGetters() {
        assertEquals(1, comment.getPostId());
        assertEquals(1, comment.getId());
        assertEquals("Sample Name", comment.getName());
        assertEquals("sample@example.com", comment.getEmail());
        assertEquals("This is a sample comment.", comment.getBody());
    }

    @Test
    void testSetters() {
        comment.setPostId(2);
        comment.setId(2);
        comment.setName("New Name");
        comment.setEmail("new@example.com");
        comment.setBody("This is a new comment.");

        assertEquals(2, comment.getPostId());
        assertEquals(2, comment.getId());
        assertEquals("New Name", comment.getName());
        assertEquals("new@example.com", comment.getEmail());
        assertEquals("This is a new comment.", comment.getBody());
    }
}

