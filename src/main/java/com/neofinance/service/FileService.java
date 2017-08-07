package com.neofinance.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    private File renameFile(File file, String toname) throws IOException {

        //检查要重命名的文件是否存在，是否是文件
        if (!file.exists() || file.isDirectory()) {
            throw new IOException("File does not exist: " + file);
        }
        File newFile = new File(toname);
        //修改文件名
        if (file.renameTo(newFile)) {
            return newFile;
        } else {
            throw new IOException("Error renaming file to name: " + toname);
        }
    }

    /**
     * Reactive模式对path路径下的文件进行批量重命名
     * @param path          服务器本地文件目录
     * @param replaceKey    文件名被替换的内容
     * @param newKey        文件名替换的新字符
     * @return              返回修改好的文件名数据流
     */
    public Flux<File> formatDirectory(String path, String replaceKey, String newKey) {
        File directory = new File(path);
        if (directory.exists() && directory.isDirectory()) {
            return Flux.just(directory.listFiles())
                    .filter(file -> file.getName().endsWith(replaceKey))
                    .map(file -> {
                                try {
                                    return renameFile(file, file.getAbsolutePath().replaceAll(replaceKey, newKey));
                                } catch (IOException exp) {
                                    exp.printStackTrace();
                                    return null;
                                }
                            }
                    );
        } else {
            return Flux.empty();
        }
    }

}
