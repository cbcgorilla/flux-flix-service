package com.neofinance;


import lombok.extern.java.Log;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Log
@SpringBootApplication
public class FluxFlixClientApplication {

    @Bean
    WebClient webClient() {
        return WebClient.builder().filter(ExchangeFilterFunctions.basicAuthentication("admin", "chenbichao"))
                .baseUrl("http://localhost:8080/api/user").build();
        // return WebClient.create("http://localhost:8080/movies");

    }

    //@Bean
    /*CommandLineRunner demo(WebClient client) {
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
    }*/
/*
    @Bean
    CommandLineRunner demo(WebClient client) {
        return strings -> {
            // FsDirectory parent = new FsDirectory(1504587637875967673L,"",0L,null);
            //FsOwner[] owners = new FsOwner[1];
            //owners[0] = new FsOwner("admin",FsOwner.Type.TYPE_PRIVATE,1);

            client.post()
                    .uri("/save")
                    .body(BodyInserters.fromObject(
                            new WebUser("", "xn042678",
                                    "坏人", "经理", "11",
                                    new HashSet<String>() {{
                                        add("administrators");
                                        add("users");
                                    }},
                                    "chenbichao@xiaoniu.com"))).retrieve()
                    .bodyToMono(String.class)
                    .subscribe(System.out::println);
        };

    }
*/

    public static void main(String[] args) {
        new SpringApplicationBuilder(FluxFlixClientApplication.class)
                .properties(Collections.singletonMap("server.port", "8099"))
                .run(args);
    }
}