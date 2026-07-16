package com.UnivTime.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_SCHEME_NAME = "bearerAuth";

//    @Value("${swagger.context-path-server}")
//    private String contextPathServer;

    @Value("${server.servlet.context-path:http://localhost:8080}")
    private String contextPathLocal;

    @Bean
    public OpenAPI customOpenAPI() {
//        Server prodServer = new Server();
//        prodServer.url(contextPathServer);
//        prodServer.setDescription("Production Server");

        Server localServer = new Server();
        localServer.url(contextPathLocal);
        localServer.setDescription("Local Server");

        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
//                .addServersItem(prodServer)
                .addServersItem(localServer)
                .info(
                        new Info()
                                .title("Swagger API 명세서")
                                .version("1.0")
                                .description("설명"))
                .components(new Components().addSecuritySchemes(BEARER_SCHEME_NAME, bearerScheme))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME_NAME));
    }

    @Bean
    public GroupedOpenApi customGroupedOpenApi() {
        return GroupedOpenApi.builder().group("api").pathsToMatch("/**").build();
    }
}