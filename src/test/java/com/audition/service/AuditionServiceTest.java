package com.audition.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.audition.dto.AuditionPostDTO;
import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class AuditionServiceTest {

    @Mock
    private AuditionIntegrationClient auditionIntegrationClient;

    @InjectMocks
    private AuditionService auditionService;

    private AuditionPost auditionPost1;
    private AuditionPostDTO auditionPostDTO1;

    private int postId = 1;
    private String title = "A Sample 1";

    List<AuditionPost> auditionPostList = new ArrayList<>();
    List<Comment> commentList = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        auditionPost1 = new AuditionPost();
        auditionPost1.setId(1);
        auditionPost1.setBody("This is a sample Post 1.");
        auditionPost1.setTitle(title);
        auditionPost1.setUserId(1001);
        Comment comment;
        comment = new Comment();
        comment.setPostId(1);
        comment.setId(1);
        comment.setName("Sample Name");
        comment.setEmail("sample@example.com");
        comment.setBody("This is a sample comment.");
        commentList.add(comment);

        auditionPost1.setComments(commentList);

        auditionPostList.add(auditionPost1);

    }

    @Test
    void testGetPostsWithFilterReturnsTrue() {
        when(auditionIntegrationClient.getPosts()).thenReturn(Optional.of(auditionPostList));
        final Optional<List<AuditionPost>> actual = auditionService.getPosts(" ");
        assertEquals(1, actual.get().size());
        assertEquals(title, actual.get().get(0).getTitle());
    }

    @Test
    void testGetPostsWithFilterReturnsFalse() {
        when(auditionIntegrationClient.getPosts()).thenReturn(Optional.ofNullable(Collections.emptyList()));
        final Optional<List<AuditionPost>> actual = auditionService.getPosts("Post Title starts with A");
        assertEquals(0, actual.get().size());
    }

    @Test
    void testGetPostByIdReturnsTrue() {
        auditionPostDTO1 = new AuditionPostDTO();
        auditionPostDTO1.setPostId(1);
        auditionPostDTO1.setBody("This is a sample Post 1.");
        auditionPostDTO1.setTitle(title);
        auditionPostDTO1.setUserId(1001);
        when(auditionIntegrationClient.getPostById(postId)).thenReturn(Optional.of(auditionPostDTO1));
        final Optional<AuditionPostDTO> actual = auditionService.getPostById(postId);
        assertTrue(actual.isPresent());
        assertEquals(auditionPostDTO1, actual.get());
    }

    @Test
    void testGetPostByIdReturnsFalse() {
        auditionPostDTO1 = new AuditionPostDTO();
        auditionPostDTO1.setPostId(1);
        auditionPostDTO1.setBody("This is a sample Post 1.");
        auditionPostDTO1.setTitle(title);
        auditionPostDTO1.setUserId(1001);

        when(auditionIntegrationClient.getPostById(postId)).thenReturn(Optional.empty());
        final Optional<AuditionPostDTO> actual = auditionService.getPostById(postId);
        assertTrue(actual.isEmpty());
    }

    @Test
    void testGetPostWithCommentsTrue() {
        when(auditionIntegrationClient.getPostWithComments(postId)).thenReturn(Optional.of(auditionPost1));
        final Optional<AuditionPost> actual = auditionService.getPostWithComments(postId);
        assertTrue(actual.isPresent());
        assertEquals(auditionPost1, actual.get());
    }

    @Test
    void testGetPostWithCommentsFalse() {
        when(auditionIntegrationClient.getPostWithComments(postId)).thenReturn(Optional.empty());
        final Optional<AuditionPost> actual = auditionService.getPostWithComments(postId);
        assertTrue(actual.isEmpty());
    }

    @Test
    void testGetCommentsForPostTrue() {
        when(auditionIntegrationClient.getCommentsForPost(postId)).thenReturn(Optional.of(commentList));
        final Optional<List<Comment>> actual = auditionService.getCommentsForPost(postId);
        assertTrue(actual.isPresent());
        assertEquals(commentList, actual.get());
    }

    @Test
    void testGetCommentsForPostFalse() {
        when(auditionIntegrationClient.getCommentsForPost(postId)).thenReturn(Optional.empty());
        final Optional<List<Comment>> actual = auditionService.getCommentsForPost(postId);
        assertTrue(actual.isEmpty());
    }
}