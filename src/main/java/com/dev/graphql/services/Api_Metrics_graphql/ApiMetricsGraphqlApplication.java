package com.dev.graphql.services.Api_Metrics_graphql;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import graphql.Scalars;
import graphql.kickstart.servlet.GraphQLHttpServlet;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import org.apache.catalina.Server;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;

@EnableSwagger2
@SpringBootApplication
	public class ApiMetricsGraphqlApplication {

		private static Map<String, Integer> m = new HashMap<>();
		private static Lock mMutex = new ReentrantLock();

		private static BlockingQueue<String> receiveMessages() throws Exception {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost:8080");
			factory.setUsername("guest");
			factory.setPassword("guest");

			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			String queueName = "QueueService";
			channel.queueDeclare(queueName, true, false, false, null);

			BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

			channel.basicConsume(queueName, true, (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");
				queue.offer(message);
			}, consumerTag -> {
			});

			return queue;
		}

		private static GraphQLObjectType createRootQuery() {
			GraphQLObjectType.Builder queryBuilder = GraphQLObjectType.newObject();
			queryBuilder.name("Query");
			queryBuilder.field(newFieldDefinition()
					.name("lastMessage")
					.type(Scalars.GraphQLString)
					.dataFetcher(environment -> m.toString())
					.build());
			return queryBuilder.build();
		}

		public static void main(String[] args) throws Exception {
			BlockingQueue<String> channel = receiveMessages();

			new Thread(() -> {
				try {
					while (true) {
						String msg = channel.take();
						String data = msg.toString();
						String formatted = data.split(":")[1];
						mMutex.lock();
						m.put(formatted, m.getOrDefault(formatted, 0) + 1);
						mMutex.unlock();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();

			GraphQLSchema schema = GraphQLSchema.newSchema()
					.query(createRootQuery())
					.build();

			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setContextPath("/");

			ServletHolder defaultServletHolder = new ServletHolder("default", DefaultServlet.class);
			defaultServletHolder.setInitParameter("resourceBase", "./public");
			context.addServlet(defaultServletHolder, "/");

			ServletHolder graphQLServletHolder = new ServletHolder(new GraphQLHttpServlet(schema));
			context.addServlet(graphQLServletHolder, "/graphql");

			Server server = new Server(1234);
			server.setHandler(context);
			server.start();
			server.join();
		}
	}