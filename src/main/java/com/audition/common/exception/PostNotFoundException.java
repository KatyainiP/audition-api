package com.audition.common.exception;

import java.io.Serializable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public PostNotFoundException(final String message, final HttpClientErrorException exception) {
        super(message, exception);
    }

    public PostNotFoundException(final String message) {
        super(message);
    }

    public PostNotFoundException(final HttpClientErrorException exception) {
        super("Post not found", exception);
    }
}
