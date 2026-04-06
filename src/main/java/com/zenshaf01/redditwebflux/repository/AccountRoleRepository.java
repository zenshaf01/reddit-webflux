package com.zenshaf01.redditwebflux.repository;

import com.zenshaf01.redditwebflux.model.AccountRole;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AccountRoleRepository extends ReactiveCrudRepository<AccountRole, Long> {
    Mono<AccountRole> findByAccountId(Long name);
}
