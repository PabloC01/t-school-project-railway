package com.tsystems.pablo_canton.railway.setup.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Info info = new Info()
                .title("Railway API")
                .version("1.0")
                .description("This API exposes endpoints to manage railway operations for clients, employees and authorization.");

        return new OpenAPI().info(info);
    }
}
