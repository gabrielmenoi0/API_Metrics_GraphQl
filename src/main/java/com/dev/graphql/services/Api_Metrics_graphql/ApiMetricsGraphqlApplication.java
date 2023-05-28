package com.dev.graphql.services.Api_Metrics_graphql;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

@EnableSwagger2
@SpringBootApplication
public class ApiMetricsGraphqlApplication {

	public static void main(String[] args) {

		SpringApplication.run(ApiMetricsGraphqlApplication.class, args);
	}

}