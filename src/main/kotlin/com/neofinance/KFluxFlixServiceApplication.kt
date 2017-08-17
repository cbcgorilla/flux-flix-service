package com.neofinance

import com.neofinance.service.StudentService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
open class KFluxFlixServiceApplication {

    @Bean
    open fun init(studentService: StudentService) = CommandLineRunner {
        studentService.initdata(100).subscribe({ println(it) })
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(KFluxFlixServiceApplication::class.java, *args)
}