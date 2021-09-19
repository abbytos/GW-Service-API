package com.globalweather.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@Configuration
@EnableSwagger2WebFlux
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("GlobalWeather Service API")
                .description("Global Weather Service API Documentation")
                .version("1.0")
                .build();
    }


     @Bean
        public Docket api() {
         return new Docket(DocumentationType.SWAGGER_2)
                 .apiInfo(apiInfo())
                 .select()
                 .apis(RequestHandlerSelectors.basePackage(("com.globalweather.swagger")))
                 .paths(PathSelectors.any())
                 .build();
        }
}