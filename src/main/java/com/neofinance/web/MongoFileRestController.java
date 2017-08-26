package com.neofinance.web;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.neofinance.service.MongoFileService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mongofiles")
public class MongoFileRestController {

    private final MongoFileService mongoFileService;

    MongoFileRestController(MongoFileService mongoFileService) {
        this.mongoFileService = mongoFileService;
    }

    @RequestMapping()
    public Flux<String> getFiles() {
        return Flux.fromIterable(mongoFileService.filenameList()).map(s->{return "【"+s.toLowerCase()+"】,\n";});
    }

    @RequestMapping("/{filename}")
    public Mono<GridFSFile> getFile(@PathVariable String filename) {
        return Mono.just(mongoFileService.getFileDescription(filename));
    }

}
