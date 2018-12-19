package com.neofinance.web;

import com.neofinance.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
public class FileRestController {

    private final FileService fileService;

    FileRestController(FileService fileService){this.fileService = fileService;}

    @RequestMapping()
    public List<File> renameFiles() {
        return fileService.renameFiles("E:\\大数据+人工智能\\Spark\\spark-summit-east-2017\\ppt","Spark-Summi.pdf",".pdf").toStream().collect(Collectors.toList());
    }

    @RequestMapping(value="/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<File> sseRenameFiles() {
        return fileService.sseRenameFiles("E:\\BaiduYunDownload\\Spark summit East 2017视频","-iteblog.mp4",".MP4");
    }

}
