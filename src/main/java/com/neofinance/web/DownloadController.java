package com.neofinance.web;

import com.neofinance.service.MongoGridFsService;
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

    private final MongoGridFsService mongoGridFsService;

    DownloadController(MongoGridFsService mongoGridFsService) {
        this.mongoGridFsService = mongoGridFsService;
    }

    @RequestMapping(value = "/servlet/{filename:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    void downloadDocument(HttpServletResponse response, @PathVariable("filename") String filename) throws IOException {
        GridFsResource fsResource = mongoGridFsService.getFileResource(filename);
        InputStream in = fsResource.getInputStream();

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.setHeader("Content-Length", String.valueOf(fsResource.contentLength()));
        FileCopyUtils.copy(in, response.getOutputStream());
    }

    @RequestMapping(value = "/entity/{filename:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    HttpEntity<byte[]> openDocumentInBrowser(@PathVariable("filename") String filename) throws IOException {
        GridFsResource fsResource = mongoGridFsService.getFileResource(filename);
        InputStream in = fsResource.getInputStream();
        byte[] document = FileCopyUtils.copyToByteArray(in);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application","pdf"));
        header.set("Content-Disposition", "inline; filename=" + filename);
        header.setContentLength(document.length);

        return new HttpEntity<byte[]>(document, header);
    }

    /**
     * 同名文件多并发请求时有冲突可能, 占用服务端本地缓存目录，需要定期清理Web服务器缓存目录
     *
     * @param response
     * @param filename
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/resource/{filename:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    Resource resourceDownload(HttpServletResponse response, @PathVariable("filename") String filename) throws IOException {
        GridFsResource fsResource = mongoGridFsService.getFileResource(filename);
        InputStream in = fsResource.getInputStream();

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", "inline; filename=" + filename);
        response.setHeader("Content-Length", String.valueOf(fsResource.contentLength()));
        return new FileSystemResource(getTempResourceFile(in, "E:\\temp\\" + filename));
    }

    private File getTempResourceFile(InputStream in, String tempFilename) {
        try {
            File f = new File(tempFilename);
            FileOutputStream out = new FileOutputStream(f);
            byte buf[] = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0)
                out.write(buf, 0, len);
            out.close();
            in.close();
            return f;
        } catch (IOException e) {
            return null;
        }
    }

}