package com.neofinance;


import com.neofinance.entity.Movie;
import com.neofinance.entity.MovieEvent;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Log
@SpringBootApplication
public class FluxFlixClientApplication {

    //@Bean
    WebClient webClient() {
        return WebClient.builder().filter(ExchangeFilterFunctions.basicAuthentication("chenwen", "password"))
                .baseUrl("http://localhost:8080/movies").build();
        // return WebClient.create("http://localhost:8080/movies");

    }

    //@Bean
    CommandLineRunner demo(WebClient client) {
        return strings ->
                client
                        .get()
                        .uri("")
                        .retrieve()
                        .bodyToFlux(Movie.class)
                        .filter(movie -> movie.getName().equalsIgnoreCase("小牛分期"))
                        .flatMap(movie ->
                                client.get()
                                        .uri("/{id}/events", movie.getId())
                                        .retrieve()
                                        .bodyToFlux(MovieEvent.class))
                        .subscribe(movieEvent -> log.info(movieEvent.toString()));
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(FluxFlixClientApplication.class)
                .properties(Collections.singletonMap("server.port", "8099"))
                .run(args);
    }
}