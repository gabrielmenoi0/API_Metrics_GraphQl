package com.dev.graphql.services.Api_Metrics_graphql.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .apis( RequestHandlerSelectors.basePackage("com.dev.graphql.services.Api_Metrics_graphql.config"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("API Metrics Graphql")
                .description("API em desenvolvimento na aula de desenvolvimento avançado de sistemas de informação")
                .version("1.0")
                .build();
    }

}
