package com.shivamtaneja.devconnect.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("Devconnect")
                    .version("1.0"))
            .servers(Arrays.asList(
                    new Server().url("http://localhost:8080").description("Development server"),
                    new Server().url("<staging-server-url>").description("Staging server"),
                    new Server().url("<prod-server-url>").description("Production server")
            ));
  }
}
