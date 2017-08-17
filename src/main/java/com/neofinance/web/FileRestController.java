package com.neofinance.web;

import com.neofinance.service.FileService;
import org.springframework.http.MediaType;
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
    public Flux<File> renameFiles() {
        return fileService.renameFiles("F:\\Spark\\spark-summit-east-2017\\ppt","-iteblog.pdf",".pdf");
    }

    @RequestMapping(value="/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<File> sseRenameFiles() {
        return fileService.sseRenameFiles("E:\\BaiduYunDownload\\Spark summit East 2017视频","-iteblog.mp4",".MP4");
    }

}
