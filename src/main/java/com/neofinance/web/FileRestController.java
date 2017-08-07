package com.neofinance.web;

import com.neofinance.service.FileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.File;

@RestController
@RequestMapping("/file")
public class FileRestController {

    private final FileService fileService;

    FileRestController(FileService fileService){this.fileService = fileService;}

    @RequestMapping()
    public Flux<File> getFiles() {
        return fileService.formatDirectory("F:\\Spark\\spark-summit-east-2017\\ppt","-iteblog.pdf",".pdf");
    }

}
