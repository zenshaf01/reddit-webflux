package com.zenshaf01.redditwebflux.handler;

import com.zenshaf01.redditwebflux.dto.LoginRequest;
import com.zenshaf01.redditwebflux.dto.RegisterRequest;
import com.zenshaf01.redditwebflux.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final AuthService authService;

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(RegisterRequest.class)
            .flatMap(authService::register)
            .flatMap(response -> ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(response)
            )
            .doOnError(e -> log.error("register error", e))
            .onErrorResume(e -> ServerResponse
                .badRequest()
                .bodyValue(e.getMessage())
            );
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
            .flatMap(authService::login)
            .flatMap(response -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(response)
            )
            .doOnError(e -> log.error("login error", e))
            .onErrorResume(e -> ServerResponse.status(HttpStatus.UNAUTHORIZED)
                .bodyValue(e.getMessage())
            );
    }
}
