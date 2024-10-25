/*
package com.audition.configuration;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class ResponseHeaderInjector implements Filter {

    @Autowired
    private Tracer tracer;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);

        final Span currentSpan = Span.current();
        final String traceId = currentSpan.getSpanContext().getTraceId();
        final String spanId = currentSpan.getSpanContext().getSpanId();
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("TraceId", traceId);
        httpResponse.setHeader("SpanId", spanId);
    }

    @Override
    public void init(final FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}*/
