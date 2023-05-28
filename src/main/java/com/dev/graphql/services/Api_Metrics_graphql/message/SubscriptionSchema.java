package com.dev.graphql.services.Api_Metrics_graphql.message;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import static graphql.Scalars.GraphQLString;

public class SubscriptionSchema {
    public static GraphQLSchema createSchema() {
        GraphQLObjectType subscriptionType = GraphQLObjectType.newObject()
                .name("Subscription")
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("messageReceived")
                        .type(GraphQLString)
                        .dataFetcher((DataFetcher<?>) new MessageReceiver("QueueService"))
                        .build())
                .build();

        return GraphQLSchema.newSchema()
                .query(GraphQLObjectType.newObject()
                        .name("Query")
                        .build())
                .subscription(subscriptionType)
                .build();
    }
}
