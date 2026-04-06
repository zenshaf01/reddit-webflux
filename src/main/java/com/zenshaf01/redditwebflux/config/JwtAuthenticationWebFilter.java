package com.zenshaf01.redditwebflux.config;

import com.zenshaf01.redditwebflux.repository.AccountRepository;
import com.zenshaf01.redditwebflux.repository.AccountRoleRepository;
import com.zenshaf01.redditwebflux.repository.RoleRepository;
import com.zenshaf01.redditwebflux.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationWebFilter implements WebFilter {

    private final JwtService jwtService;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountRoleRepository accountRoleRepository;

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        String token = extractUsername(exchange);
        if (token == null || !jwtService.isTokenValid(token)) {
            return chain.filter(exchange);
        }

        String username = jwtService.extractUsername(token);
        return accountRepository.findByUsername(username)
            .flatMap(account ->
                accountRoleRepository.findByAccountId(account.getId())
                    .flatMap(accountRole -> roleRepository.findById(accountRole.getRoleId()))
                    .map(role -> new SimpleGrantedAuthority(role.getName())) // ← no prefix needed, DB has it
                    .flux().collectList()
                    .map(authorities -> new UsernamePasswordAuthenticationToken(
                            account, null, authorities
                    ))
            )
            .flatMap(auth ->
                    chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
            );
}

    private String extractUsername(ServerWebExchange exchange) {
        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
