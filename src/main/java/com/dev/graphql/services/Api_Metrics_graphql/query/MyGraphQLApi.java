package com.dev.graphql.services.Api_Metrics_graphql.query;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyGraphQLApi {
    private final Counter graphqlApiCalls;

    @Autowired
    public MyGraphQLApi(MeterRegistry registry) {
        this.graphqlApiCalls = registry.counter("graphql.api.calls", "description", "Number of calls to the GraphQL API");
    }

    public Object executeQuery(String query) {
        // Realiza a lógica do GraphQL aqui
        graphqlApiCalls.increment();
        // Retorna o resultado da query
        return null;
    }
}
