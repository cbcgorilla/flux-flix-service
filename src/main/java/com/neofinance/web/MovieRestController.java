package com.neofinance.web;

import com.neofinance.service.FluxFlixService;
import com.neofinance.entity.Movie;
import com.neofinance.entity.MovieEvent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/movies")
public class MovieRestController {

    private final FluxFlixService fluxFlixService;

    MovieRestController(FluxFlixService fluxFlixService) {
        this.fluxFlixService = fluxFlixService;
    }

    @RequestMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MovieEvent> events(@PathVariable String id) {
        return fluxFlixService.byId(id).flatMapMany(fluxFlixService::streamStreams);
    }

    @RequestMapping(produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Movie> all() {
        return fluxFlixService.all(100);
    }

    @RequestMapping("/{id}")
    public Mono<Movie> byId(@PathVariable String id) {
        return fluxFlixService.byId(id);
    }

    @RequestMapping("/count")
    public Mono<Long> count() {
        return fluxFlixService.count();
    }

    @RequestMapping("/delete")
    public Mono<Long> deleteAll() {
        return fluxFlixService.deleteAll();
    }

    @RequestMapping("/add/{name}")
    public Mono<Movie> addMovie(@PathVariable String name) {
        return fluxFlixService.addMovie(new Movie(UUID.randomUUID().toString(), name, randomGenre()));
    }

    @RequestMapping(value = "/batchadd/{size}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Movie> batchAddMovie(@PathVariable Integer size) {
        return fluxFlixService.batchAddMovie(size, 100);
    }

    private String randomGenre() {
        String[] genres = "恐怖,灾难,喜剧,悲剧,动作,战争,爱情,歌剧,纪录片".split(",");
        return genres[new Random().nextInt(genres.length)];
    }
}
