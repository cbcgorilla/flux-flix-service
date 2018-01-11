package com.neofinance;

import com.neofinance.service.FluxFlixService;
import com.neofinance.service.MongoGridFsService;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
            fluxflixService.initdata(50).subscribe(System.out::println);
        };
    }

    @Bean
    CommandLineRunner demo2(MongoGridFsService mongoGridFsService) {
        return args -> {
            File directory = new File("E:\\Download");
            if (directory.exists() && directory.isDirectory()) {
                Flux.just(directory.listFiles())
                        .filter(file -> file.getName().toLowerCase().endsWith(".pdf"))
                        .map(file -> {
                                    try {
                                        mongoGridFsService.storeFile(new FileInputStream(file), file.getName(), "PDF");
                                    } catch (IOException exp) {
                                        exp.printStackTrace();
                                    }
                                    return file;
                                }
                        ).subscribe();
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(FluxFlixServiceApplication.class, args);
    }
}
