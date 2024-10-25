package com.audition.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.audition.common.exception.SystemException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class SystemExceptionTest {

    static final String ROOT_CAUSE = "root cause";
    static final String ERROR_DETAIL = "An error detail";

    @Test
    void testSystemExceptionWithMessage() {
        final String message = "An error occurred";
        final SystemException exception = new SystemException(message);
        assertEquals(message, exception.getMessage());
        assertEquals(SystemException.DEFAULT_TITLE, exception.getTitle());
        assertEquals(null, exception.getStatusCode());
        assertEquals(null, exception.getDetail());
    }

    @Test
    void testSystemExceptionWithMessageAndErrorCode() {
        final String message = "An error occurred";
        final Integer errorCode = 404;
        final SystemException exception = new SystemException(message, errorCode);
        assertEquals(message, exception.getMessage());
        assertEquals(SystemException.DEFAULT_TITLE, exception.getTitle());
        assertEquals(errorCode, exception.getStatusCode());
        assertEquals(null, exception.getDetail());
    }

    @Test
    void testSystemExceptionWithMessageAndThrowable() {
        final String message = "An error occurred";
        final Throwable cause = new RuntimeException();
        final SystemException exception = new SystemException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(SystemException.DEFAULT_TITLE, exception.getTitle());
        assertEquals(null, exception.getStatusCode());
        assertEquals(null, exception.getDetail());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testSystemExceptionWithDetailTitleAndErrorCode() {
        final String title = "Custom Title";
        final Integer errorCode = 400;
        final SystemException exception = new SystemException(ERROR_DETAIL, title, errorCode);
        assertEquals(ERROR_DETAIL, exception.getMessage());
        assertEquals(title, exception.getTitle());
        assertEquals(errorCode, exception.getStatusCode());
        assertEquals(ERROR_DETAIL, exception.getDetail());
    }

    @Test
    void testSystemExceptionWithDetailTitleAndThrowable() {
        final String detail = "An error detail";
        final String title = "Custom Title";
        final Throwable cause = new RuntimeException(ROOT_CAUSE);
        final SystemException exception = new SystemException(detail, title, cause);
        assertEquals(detail, exception.getMessage());
        assertEquals(title, exception.getTitle());
        assertEquals(Integer.valueOf(500), exception.getStatusCode());
        assertEquals(detail, exception.getDetail());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testSystemExceptionWithDetailErrorCodeAndThrowable() {
        final Integer errorCode = 500;
        final Throwable cause = new RuntimeException(ROOT_CAUSE);
        final SystemException exception = new SystemException(ERROR_DETAIL, errorCode, cause);
        assertEquals(ERROR_DETAIL, exception.getMessage());
        assertEquals(SystemException.DEFAULT_TITLE, exception.getTitle());
        assertEquals(errorCode, exception.getStatusCode());
        assertEquals(ERROR_DETAIL, exception.getDetail());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testSystemExceptionWithAllParameters() {
        final String title = "Custom Title";
        final Integer errorCode = 403;
        final Throwable cause = new RuntimeException(ROOT_CAUSE);
        final SystemException exception = new SystemException(ERROR_DETAIL, title, errorCode, cause);
        assertEquals(ERROR_DETAIL, exception.getMessage());
        assertEquals(title, exception.getTitle());
        assertEquals(errorCode, exception.getStatusCode());
        assertEquals(ERROR_DETAIL, exception.getDetail());
        assertEquals(cause, exception.getCause());
    }
}
