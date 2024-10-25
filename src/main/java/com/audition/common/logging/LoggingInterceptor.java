package com.audition.common.logging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
        final ClientHttpRequestExecution execution)
        throws IOException {

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Request URI: {}", request.getURI());
            LOGGER.info("Request Method: {}", request.getMethod());
            LOGGER.info("Request Body: {}",
                new String(body, StandardCharsets.UTF_8));
        }

        // Execute the request
        final ClientHttpResponse response = execution.execute(request, body);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Response Status Code: {}", response.getStatusCode());
            LOGGER.info("Response Body: {}", response.getBody());
        }
        return response;
    }
}
