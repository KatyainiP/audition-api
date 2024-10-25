package com.audition.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.audition.common.exception.PostNotFoundException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(MockitoJUnitRunner.class)
class PostNotFoundExceptionTest {

    static final String ERROR_MESSAGE = "Post not found with ID";

    @Test
    void testPostNotFoundExceptionWithMessage() {
        final PostNotFoundException exception = new PostNotFoundException(ERROR_MESSAGE);
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void testPostNotFoundExceptionWithMessageAndCause() {
        final HttpHeaders headers = new HttpHeaders(); // Create a non-null HttpHeaders object
        final HttpClientErrorException cause = HttpClientErrorException.create(
            HttpStatus.NOT_FOUND,
            "Not Found",
            headers,
            new byte[0],
            StandardCharsets.UTF_8
        );

        final PostNotFoundException exception = new PostNotFoundException(ERROR_MESSAGE, cause);
        assertEquals(ERROR_MESSAGE, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testPostNotFoundExceptionWithCause() {
        final HttpHeaders headers = new HttpHeaders(); // Create a non-null HttpHeaders object
        final HttpClientErrorException cause = HttpClientErrorException.create(
            HttpStatus.NOT_FOUND,
            "Not Found",
            headers,
            new byte[0],
            StandardCharsets.UTF_8
        );

        final PostNotFoundException exception = new PostNotFoundException(cause);
        assertEquals("Post not found", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testPostNotFoundExceptionIsRuntimeException() {
        assertThrows(PostNotFoundException.class, () -> {
            throw new PostNotFoundException("Test message");
        });
    }
}
