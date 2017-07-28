package com.neofinance;

import com.neofinance.service.FluxFlixService;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Log
@SpringBootApplication
public class FluxFlixServiceApplication {
/*
    @Bean
    RouterFunction<?> routes(FluxFlixService ffs) {
        return RouterFunctions
                .route(GET("/movies"),
                        serverRequest -> ServerResponse.ok().body(ffs.all(), Movie.class))
                .andRoute(GET("/movies/count"),
                        serverRequest -> ServerResponse.ok().body(ffs.count(), Long.class))
                .andRoute(GET("/moviesdelete"),
                        serverRequest -> ServerResponse.ok().body(ffs.deleteAll(), Void.class))
                .andRoute(GET("/movies/{id}"),
                        serverRequest -> ServerResponse.ok().body(ffs.byId(serverRequest.pathVariable("id")), Movie.class))
                .andRoute(GET("/movies/{id}/events"), serverRequest ->
                        ServerResponse.ok()
                                .contentType(MediaType.TEXT_EVENT_STREAM)
                                .body(ffs.byId(serverRequest.pathVariable("id")).flatMapMany(ffs::streamStreams), MovieEvent.class));
        // .andRoute(GET("/users/me"), uh::current);
    }*/

    @Bean
    CommandLineRunner demo(FluxFlixService fluxflixService) {
        return args -> {
            //fluxflixService.initdata(5000000).subscribe(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(FluxFlixServiceApplication.class, args);
    }

}
