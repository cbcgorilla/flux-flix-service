package com.neofinance.web

import com.neofinance.service.Student
import com.neofinance.service.StudentService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/students")
class StudentController(val studentService: StudentService) {

    @RequestMapping(produces = arrayOf(MediaType.TEXT_EVENT_STREAM_VALUE))
    fun all(): Flux<Student> {
        return studentService.all(100)
    }

    @RequestMapping("/count")
    fun count(): Mono<Long> {
        return studentService.count()
    }

    @GetMapping("/{name}")
    fun getStudentByName(@PathVariable name: String): Mono<Student> {
        return studentService.byName(name)
    }

    @GetMapping("/age/{gt}to{lt}")
    fun byAgeBetween(@PathVariable gt: Int, @PathVariable lt: Int): Flux<Student> {
        return studentService.byAgeBetween(gt, lt)
    }
}