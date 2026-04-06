package com.zenshaf01.redditwebflux.service;

import com.zenshaf01.redditwebflux.dto.AuthResponse;
import com.zenshaf01.redditwebflux.dto.LoginRequest;
import com.zenshaf01.redditwebflux.dto.RegisterRequest;
import com.zenshaf01.redditwebflux.model.Account;
import com.zenshaf01.redditwebflux.model.AccountRole;
import com.zenshaf01.redditwebflux.repository.AccountRepository;
import com.zenshaf01.redditwebflux.repository.AccountRoleRepository;
import com.zenshaf01.redditwebflux.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final AccountRoleRepository accountRoleRepository;

    // register
    public Mono<AuthResponse> register(RegisterRequest request) {
        return accountRepository.existsByUsername(request.getUsername())
            // check username
            .flatMap(exists -> {
                if (exists) {
                    return Mono.error(new RuntimeException("Username is already in use"));
                }
                return accountRepository.existsByEmail(request.getEmail());
            })
            // check email
            .flatMap(emailTaken -> {
                if(emailTaken) {
                    return Mono.error(new RuntimeException("Email is already in use"));
                }
                Account account = Account.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .passwordHash(passwordEncoder.encode(request.getPassword()))
                    .karma(1)
                    .build();
                return accountRepository.save(account);
            })
            .flatMap(saved -> roleRepository.findByName("User")
                .flatMap(role -> accountRoleRepository.save(
                        new AccountRole(saved.getId(), role.getId())
                )).thenReturn(saved)
            )
            // create auth response
            .map(saved -> new AuthResponse(jwtService.generateToken(saved)));
    }

    public Mono<AuthResponse> login(LoginRequest request) {
        return accountRepository.findByUsername(request.getUsername())
            .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")))
            .flatMap(account -> {
                if(!passwordEncoder.matches(request.getPassword(), account.getPasswordHash())) {
                    return Mono.error(new RuntimeException("Invalid credentials"));
                }
                return Mono.just(new AuthResponse(jwtService.generateToken(account)));
            });
    }
}
