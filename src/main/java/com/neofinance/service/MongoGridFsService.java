package com.neofinance.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MongoGridFsService {

    private final GridFsTemplate gridFsTemplate;

    @Autowired
    public MongoGridFsService(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    /**
     * 存储文件， 如同名文件已存在则更新文件内容
     *
     * @param file        输入流文件
     * @param filename    输入文件名
     * @param contentType 文件类型
     * @return
     */
    public boolean storeFile(InputStream file, String filename, String contentType) {
        Optional<GridFSFile> existing = checkStoredFile(filename);
        if (existing.isPresent()) {
            gridFsTemplate.delete(filenameQuery(filename));
        }
        gridFsTemplate.store(file, filename, contentType);
        System.out.println("存储文件： " + filename);
        return true;
    }

    public List<String> filenameList() {
        return StreamSupport.stream(
                gridFsTemplate.find(null)
                        .map(GridFSFile::getFilename)
                        .spliterator(), false)
                .collect(Collectors.toList());
    }

    public GridFSFile getFileDescription(String name) {
        Optional<GridFSFile> optionalCreated = checkStoredFile(name);
        if (optionalCreated.isPresent()) {
            return optionalCreated.get();
        } else {
            return null;
        }
    }

    public GridFsResource getFileResource(String filename) {
        return gridFsTemplate.getResource(filename);
    }

    private Optional<GridFSFile> checkStoredFile(String name) {
        GridFSFile file = gridFsTemplate.findOne(filenameQuery(name));
        return Optional.ofNullable(file);
    }

    private static Query filenameQuery(String name) {
        return Query.query(GridFsCriteria.whereFilename().is(name));
    }
}
