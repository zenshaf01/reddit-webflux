package com.zenshaf01.redditwebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.oauth2.client.autoconfigure.reactive.ReactiveOAuth2ClientAutoConfiguration;

@SpringBootApplication()
public class RedditWebfluxApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedditWebfluxApplication.class, args);
    }
}
