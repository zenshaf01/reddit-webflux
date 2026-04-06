package com.zenshaf01.redditwebflux.repository;

import com.zenshaf01.redditwebflux.model.Account;
import lombok.NonNull;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {
    @NonNull
    Mono<Account> findById(Long id);

    Mono<Account> findByUsername(String username);
    Mono<Account> findByEmail(String email);

    Mono<Boolean> existsByUsername(String username);
    Mono<Boolean> existsByEmail(String email);
}
