package com.zenshaf01.redditwebflux.router;

import com.zenshaf01.redditwebflux.handler.AuthHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
class AuthRouter {

    private final AuthHandler authHandler;

    @Bean
    RouterFunction<ServerResponse> authRoutes() {
        return RouterFunctions.route()
            .POST("/auth/register", authHandler::register)
            .POST("/auth/login", authHandler::login)
            .build();
    }
}
