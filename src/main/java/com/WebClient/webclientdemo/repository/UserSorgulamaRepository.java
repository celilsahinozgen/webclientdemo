package com.WebClient.webclientdemo.repository;

import com.WebClient.webclientdemo.model.UserSorgulama;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserSorgulamaRepository extends ReactiveCrudRepository<UserSorgulama, String> {

    Mono<UserSorgulama> findByTcNo(String tcNo);

}
