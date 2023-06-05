package com.dev.graphql.services.Api_Metrics_graphql.message;
import java.nio.channels.Channel;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.rabbitmq.client.ConnectionFactory;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLObjectType;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;


public class MessageReceiver {
    private final ConnectionFactory factory;
    private final String queueName;

    public MessageReceiver(String queueName) {
        this.factory = new ConnectionFactory();
        this.factory.setHost("localhost");
        this.factory.setUsername("guest");
        this.factory.setPassword("guest");
        this.queueName = queueName;
    }


}
