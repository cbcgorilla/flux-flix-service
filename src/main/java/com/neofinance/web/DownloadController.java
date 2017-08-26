package com.neofinance.web;

import com.neofinance.service.MongoFileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/download")
public class DownloadController {

    private static final String APPLICATION_PDF = "application/pdf";

    private final MongoFileService mongoFileService;

    DownloadController(MongoFileService mongoFileService) {
        this.mongoFileService = mongoFileService;
    }

    @RequestMapping(value = "/a/{filename:.+}", method = RequestMethod.GET, produces = APPLICATION_PDF)
    public @ResponseBody
    void downloadDocument(HttpServletResponse response, @PathVariable("filename") String filename) throws IOException {
        GridFsResource fsResource = mongoFileService.getFileResource(filename);
        InputStream in = fsResource.getInputStream();

        response.setContentType(APPLICATION_PDF);
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.setHeader("Content-Length", String.valueOf(fsResource.contentLength()));
        FileCopyUtils.copy(in, response.getOutputStream());
    }

    @RequestMapping(value = "/b/{filename:.+}", method = RequestMethod.GET, produces = APPLICATION_PDF)
    public @ResponseBody
    HttpEntity<byte[]> openDocumentInBrowser(@PathVariable("filename") String filename) throws IOException {
        GridFsResource fsResource = mongoFileService.getFileResource(filename);
        InputStream in = fsResource.getInputStream();
        byte[] document = FileCopyUtils.copyToByteArray(in);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "pdf"));
        header.set("Content-Disposition", "inline; filename=" + filename);
        header.setContentLength(document.length);

        return new HttpEntity<byte[]>(document, header);
    }
/*
    @RequestMapping(value = "/c/{filename}", method = RequestMethod.GET, produces = APPLICATION_PDF)
    public @ResponseBody
    Resource downloadC(HttpServletResponse response, @PathVariable String filename) throws IOException {
        GridFsResource fsResource = mongoFileService.getFileResource(filename);
        InputStream in = fsResource.getInputStream();

        response.setContentType(APPLICATION_PDF);
        response.setHeader("Content-Disposition", "inline; filename=" + filename);
        response.setHeader("Content-Length", String.valueOf(fsResource.contentLength()));
        return new FileSystemResource(in);
    }*/

}