package com.audition.web;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.audition.dto.AuditionPostDTO;
import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import com.audition.service.AuditionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
class AuditionControllerTest {

    @Mock
    private AuditionService auditionService;

    @InjectMocks
    private AuditionController auditionController;

    private MockMvc mockMvc;

    List<AuditionPost> auditionPostList = new ArrayList<>();
    List<Comment> commentList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        AuditionPost auditionPost;
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(auditionController).build();

        auditionPost = new AuditionPost();
        auditionPost.setId(1);
        auditionPost.setBody("In Post Body");
        auditionPost.setTitle("Post Title");
        auditionPost.setUserId(1);

        final Comment comment = new Comment();
        comment.setPostId(1);
        comment.setId(1);
        comment.setName("Sample");
        comment.setEmail("Sample");
        comment.setBody("Sample");
        commentList.add(comment);

        auditionPost.setComments(commentList);
        auditionPostList.add(auditionPost);
    }

    @Test
    void testGetPostsWithFilterReturnsOk() throws Exception {
        when(auditionService.getPosts(" ")).thenReturn(Optional.of(auditionPostList));
        final MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                .param("filter", " ")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json("[{\"postId\":1,\"userId\":1,\"title\":\"Post Title\",\"body\":\"In Post Body\"}]"))
            .andDo(print())
            .andReturn();
        assertNotNull(result, "MvcResult should not be null");

    }

    @Test
    void testGetPostByIdReturnsOk() throws Exception {
        final AuditionPostDTO post = new AuditionPostDTO();
        post.setPostId(1);
        post.setUserId(1);
        post.setBody("In Post Body");
        post.setTitle("Post Title");
        when(auditionService.getPostById(1)).thenReturn(Optional.of(post));
        final MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/posts/1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content()
                .json("{\"postId\":1,\"userId\":1,\"title\":\"Post Title\",\"body\":\"In Post Body\"}"))
            .andDo(print())
            .andReturn();
        assertNotNull(result, "MvcResult should not be null");
    }

    @Test
    void testGetCommentsForPostReturnsComments() throws Exception {
        when(auditionService.getCommentsForPost(1)).thenReturn(Optional.of(commentList));

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/posts/{id}/comments", 1)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(
                "[{\"id\":1,\"postId\":1,\"name\":\"Sample\",\"email\":\"Sample\",\"body\":\"Sample\"}]"))
            .andReturn();
        assertNotNull(result, "MvcResult should not be null");
    }
}