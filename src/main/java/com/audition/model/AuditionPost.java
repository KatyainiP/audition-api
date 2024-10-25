package com.audition.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("PMD.DataClass")
public class AuditionPost {

    private int userId;
    private int id;
    private String title;
    private String body;
    private List<Comment> comments;

    public AuditionPost(final int userId, final int id, final String title, final String body, final List<Comment> comments) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
        this.comments = new ArrayList<>(comments);
    }

    public AuditionPost() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return id;
    }

    public void setPostId(final int postId) {
        this.id = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    public void setComments(final List<Comment> comments) {
        this.comments = new ArrayList<>(comments);
    }
}

