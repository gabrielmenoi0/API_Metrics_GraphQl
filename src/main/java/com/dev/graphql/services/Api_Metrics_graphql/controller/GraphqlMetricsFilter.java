package com.dev.graphql.services.Api_Metrics_graphql.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class GraphqlMetricsFilter extends GenericFilterBean {

    private final Counter graphqlRequestCount;

    public GraphqlMetricsFilter(MeterRegistry meterRegistry) {
        graphqlRequestCount = Counter.builder("graphql.request.count")
                .description("Number of GraphQL requests")
                .register(meterRegistry);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            String requestURI = ((HttpServletRequest) request).getRequestURI();
            if (requestURI.endsWith("/graphql")) {
                graphqlRequestCount.increment();
            }
        }
        chain.doFilter(request, response);
    }
}

