package com.dev.graphql.services.Api_Metrics_graphql;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

//    @RabbitListener(queues = "${javainuse.rabbitmq.queue}")
//    public void recievedMessage(Employee employee) {
//        System.out.println("Recieved Message From RabbitMQ: " + employee);
//    }
}