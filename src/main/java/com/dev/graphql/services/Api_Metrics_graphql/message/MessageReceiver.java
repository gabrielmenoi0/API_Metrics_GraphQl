package com.dev.graphql.services.Api_Metrics_graphql.message;
import java.nio.channels.Channel;
import java.sql.Connection;
import com.rabbitmq.client.ConnectionFactory;
import graphql.schema.DataFetchingEnvironment;


public class MessageReceiver implements DataFetcher<SubscriptionMessage> {
    private final ConnectionFactory factory;
    private final String queueName;

    public MessageReceiver(String queueName) {
        this.factory = new ConnectionFactory();
        this.factory.setHost("localhost");
        this.factory.setUsername("guest");
        this.factory.setPassword("guest");
        this.queueName = queueName;
    }

    public MessageReceiver(ConnectionFactory factory, String queueName) {
        this.factory = factory;
        this.queueName = queueName;
    }

    public SubscriptionMessage get(DataFetchingEnvironment environment) throws Exception {
        Connection connection = new factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, true, false, false, null);
        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        String message = new String(delivery.getBody(), "UTF-8");

        return new SubscriptionMessage(message);
    }
}
