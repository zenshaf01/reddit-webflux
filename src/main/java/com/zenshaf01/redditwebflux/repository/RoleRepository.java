package com.zenshaf01.redditwebflux.repository;

import com.zenshaf01.redditwebflux.model.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {
    Mono<Role> findByName(String name);
}
