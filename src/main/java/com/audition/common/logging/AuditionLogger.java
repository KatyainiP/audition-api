package com.audition.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

@Component
public class AuditionLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditionLogger.class);

    public void info(final Logger logger, final String message) {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    public void info(final Logger logger, final String message, final Object object) {
        if (logger.isInfoEnabled()) {
            logger.info(message, object);
        }
    }

    public void debug(final Logger logger, final String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    public void warn(final Logger logger, final String message) {
        if (logger.isWarnEnabled()) {
            logger.warn(message);
        }
    }

    public void error(final Logger logger, final String message) {
        if (logger.isErrorEnabled()) {
            logger.error(message);
        }
    }

    public void logErrorWithException(final Logger logger, final String message, final Exception e) {
        if (logger.isErrorEnabled()) {
            logger.error(message, e);
        }
    }

    public void logStandardProblemDetail(final Logger logger, final ProblemDetail problemDetail, final Exception e) {
        if (logger.isErrorEnabled()) {
            final var message = createStandardProblemDetailMessage(problemDetail);
            logger.error(message, e);
        }
    }

    public void logHttpStatusCodeError(final Logger logger, final String message, final Integer errorCode) {
        if (logger.isErrorEnabled()) {
            logger.error(createBasicErrorResponseMessage(errorCode, message) + "\n");
        }
    }

    private String createStandardProblemDetailMessage(final ProblemDetail standardProblemDetail) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("ProblemDetail: Status: {}, Title: {}, Detail: {}, Additional Info: {}",
                standardProblemDetail.getStatus(),
                standardProblemDetail.getTitle(),
                standardProblemDetail.getDetail(),
                "Logging.."); // Provide the fourth argument as needed
        }
        return String.format("Error occurred: %s (Status: %d) - %s",
            standardProblemDetail.getTitle(),
            standardProblemDetail.getStatus(),
            standardProblemDetail.getDetail());
    }

    private String createBasicErrorResponseMessage(final Integer errorCode, final String message) {
        LOGGER.error("Error occurred - Code: {}, Message: {}", errorCode, message);
        return String.format("Error Code: %d, Message: %s", errorCode, message);
    }
}
