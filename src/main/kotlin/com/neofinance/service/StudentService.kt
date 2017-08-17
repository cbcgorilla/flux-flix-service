package com.neofinance.service

import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.*
import java.util.stream.Stream

data class Student(val name: String, val age: Int = 0, val scores: Array<Double>)


interface ReactiveStudentRepository : ReactiveMongoRepository<Student, Long> {
    fun findByName(name: String): Mono<Student>

    @Query("{ 'age' : { \$gt: ?0, \$lt: ?1 } }")
    fun findStudentByAgeBetween(ageGT: Int, ageLT: Int): Flux<Student>

}

@Service
class StudentService(val studentRepository: ReactiveStudentRepository) {

    private fun randomUser(): String {
        var names = "Flux Movie,Reactive Mongos back to the World,小牛分期,电影,时尚, 美丽,人生喜剧,小牛普惠,重出江湖,小牛金服".split(",".toRegex())
        return names[Random().nextInt(names.size)].trim()
    }

    fun initdata(limit: Long): Mono<Void> {
        return studentRepository.deleteAll().thenMany(
                Flux.fromStream(
                        Stream.generate { Student(randomUser(), (Math.random() * 100).toInt(), Array(5, { Math.random() * 100 })) }.limit(limit))
                        .flatMap<Student> { studentRepository.save(it) }).then()
    }

    fun all(milliSpeed: Long): Flux<Student> {
        val interval = Flux.interval(Duration.ofMillis(milliSpeed))
        return Flux.zip<Long, Student>(interval, studentRepository.findAll()).map { it.getT2() }
    }

    fun count() = studentRepository.count()

    fun byName(name: String) = studentRepository.findByName(name)

    fun byAgeBetween(ageGT: Int, ageLT: Int) = studentRepository.findStudentByAgeBetween(ageGT, ageLT)

}