package com.neofinance.web

import com.neofinance.service.StudentService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/students")
class StudentRestAPI(val studentService: StudentService) {

    @RequestMapping(produces = arrayOf(MediaType.TEXT_EVENT_STREAM_VALUE))
    fun all() = studentService.all(100)

    @RequestMapping("/count")
    fun count()= studentService.count()

    @GetMapping("/{name}")
    fun getStudentByName(@PathVariable name: String) = studentService.byName(name)

    @GetMapping("/age/{gt}to{lt}")
    fun byAgeBetween(@PathVariable gt: Int, @PathVariable lt: Int) = studentService.byAgeBetween(gt, lt)

}