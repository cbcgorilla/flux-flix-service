package com.neofinance.web;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.neofinance.service.MongoGridFsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mongofiles")
public class GridFsRestController {

    private final MongoGridFsService mongoGridFsService;

    GridFsRestController(MongoGridFsService mongoGridFsService) {
        this.mongoGridFsService = mongoGridFsService;
    }

    @RequestMapping()
    public Flux<String> getFiles() {
        return Flux.fromIterable(mongoGridFsService.filenameList()).map(s -> "【"+s+"】\n");
    }

    @RequestMapping("/{filename}")
    public Mono<GridFSFile> getFile(@PathVariable String filename) {
        return Mono.just(mongoGridFsService.getFileDescription(filename));
    }

}
