package com.neofinance.service;

import com.neofinance.entity.Movie;
import com.neofinance.entity.MovieEvent;
import lombok.extern.java.Log;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@Log
@Service
public class FluxFlixService {

    private final MovieRepository movieRepository;

    FluxFlixService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Mono<Void> initdata(Integer limit) {
        return movieRepository.deleteAll().thenMany(
                Flux.fromStream(
                        Stream.generate(() -> new Movie(UUID.randomUUID().toString(), randomName(), randomGenre())).limit(limit))
                        .flatMap(m -> movieRepository.save(m))).then();
    }

    public Flux<MovieEvent> streamStreams(Movie movie) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<MovieEvent> events = Flux.fromStream(Stream.generate(() -> new MovieEvent(movie, new Date(), randomUser())));
        return Flux.zip(interval, events).map(Tuple2::getT2);
    }

    private String randomUser() {
        String[] users = "Michael, Bob, Bill, Buffet, JavaFxExpert".split(",");
        return users[new Random().nextInt(users.length)];
    }

    public Flux<Movie> all(Integer milliSpeed) {
        Flux<Long> interval = Flux.interval(Duration.ofMillis(milliSpeed));
        return Flux.zip(interval, movieRepository.findAll()).map(Tuple2::getT2);
    }

    public Mono<Long> count() {
        return movieRepository.count();
    }

    public Mono<Movie> byId(String id) {
        return movieRepository.findById(id);
    }

    private String randomName() {
        String[] names = "Flux Movie,Reactive Mongos back to the World,小牛分期,电影,时尚,美丽,人生喜剧,小牛普惠,重出江湖,小牛金服".split(",");
        return names[new Random().nextInt(names.length)];
    }

    private String randomGenre() {
        String[] genres = "恐怖,灾难,喜剧,悲剧,动作,战争,爱情,歌剧,纪录".split(",");
        return genres[new Random().nextInt(genres.length)];
    }

    public Mono<Movie> addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Flux<Movie> batchAddMovie(Integer size, Integer milliSpeed) {
        Flux<Long> interval = Flux.interval(Duration.ofMillis(milliSpeed));
        Flux<Movie> movies = Flux.fromStream(
                Stream.generate(() -> new Movie(UUID.randomUUID().toString(), randomName(), randomGenre())).limit(size))
                .flatMap(movieRepository::save);
        return Flux.zip(interval, movies).map(Tuple2::getT2);
    }

    public Mono<Long> deleteAll() {
        return movieRepository.deleteAll().thenMany(Flux.fromStream(
                Stream.generate(() -> new Movie(UUID.randomUUID().toString(), randomName(), randomGenre())).limit(10))
                .flatMap(movieRepository::save)).count();
    }

}

interface MovieRepository extends ReactiveMongoRepository<Movie, String> {

}
