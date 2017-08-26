package com.neofinance.security;
/*
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
@Component*/
public class UserHandler {
 /*   private final UserDetailsRepository udr;

    UserHandler(UserDetailsRepository udr) {
        this.udr = udr;
    }

    public Mono<ServerResponse> byUsername(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(udr.findByUsername(request.pathVariable("username")), UserDetails.class);
    }


    public Mono<ServerResponse> current(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request.principal()
                                .cast(Authentication.class)
                                .map(Authentication::getPrincipal)
                                .cast(UserDetails.class),
                        UserDetails.class
                );
    }*/
}
