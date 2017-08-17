package com.neofinance.service

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.*
import java.util.stream.Stream

data class Student(val name: String, val age: Int, val scores: Array<Double>)

interface StudentRepository : ReactiveMongoRepository<Student, String> {
    fun findByName(name: String): Mono<Student>
}

@Service
class StudentService(val studentRepository: StudentRepository) {

    private fun randomUser(): String {
        val users = "Michael, Bob, Bill, Buffet, JavaFxExpert".split(",")
        return users[Random().nextInt(users.size)]
    }

    fun initdata(limit: Long): Mono<Void> {
        return studentRepository.deleteAll().thenMany(
                Flux.fromStream(
                        Stream.generate { Student(randomUser(), (Math.random() * 100).toInt(), Array(5, { i -> (Math.random() * 100) })) }.limit(limit))
                        .flatMap<Student> { m -> studentRepository.save(m) }).then()
    }

    fun all(milliSpeed: Long): Flux<Student> {
        val interval = Flux.interval(Duration.ofMillis(milliSpeed))
        return Flux.zip<Long, Student>(interval, studentRepository.findAll()).map { it.getT2() }
    }

    fun count(): Mono<Long> {
        return studentRepository.count()
    }

    fun byName(name: String): Mono<Student> {
        return studentRepository.findByName(name)
    }

}