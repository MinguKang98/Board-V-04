package com.study.boardv04back.util;

import com.study.boardv04back.domain.File;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtils {

    @Value("${file.download-location}")
    private String DOWNLOAD_DIR;
    private String SEPARATOR = java.io.File.separator;

    public List<File> getAddFileList(List<MultipartFile> multipartFileList ) throws IOException {

        List<File> fileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            if (multipartFile !=null && !multipartFile.isEmpty()) {
                String originFileName = multipartFile.getOriginalFilename();
                String fileExtension = FilenameUtils.getExtension(originFileName).toLowerCase();
                String systemFileName = UUID.randomUUID() + "." + fileExtension;

                java.io.File uploadFile = new java.io.File(DOWNLOAD_DIR + SEPARATOR + systemFileName);
                multipartFile.transferTo(uploadFile);

                File file = File.builder()
                        .originName(originFileName)
                        .systemName(systemFileName)
                        .build();

                fileList.add(file);
            }
        }

        return fileList;
    }

    public List<File> getAddFileList(List<MultipartFile> multipartFileList, int boardId) throws IOException {

        List<File> fileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            if (multipartFile !=null && !multipartFile.isEmpty()) {
                String originFileName = multipartFile.getOriginalFilename();
                String fileExtension = FilenameUtils.getExtension(originFileName).toLowerCase();
                String systemFileName = UUID.randomUUID() + "." + fileExtension;

                java.io.File uploadFile = new java.io.File(DOWNLOAD_DIR + SEPARATOR + systemFileName);
                multipartFile.transferTo(uploadFile);

                File file = File.builder()
                        .originName(originFileName)
                        .systemName(systemFileName)
                        .boardId(boardId)
                        .build();

                fileList.add(file);
            }
        }

        return fileList;
    }

    public List<File> getDeleteFileList(List<File> originFileList) throws IOException {

        List<File> fileList = new ArrayList<>();
        for (File originFile : originFileList) {
            String fileDeleteCheck = "FILE_" + originFile.getFileId();
//            if (request.getParameter(fileDeleteCheck) == null) {
//                fileList.add(originFile);
//
//                java.io.File deleteFile = new java.io.File(DOWNLOAD_DIR + SEPARATOR + originFile.getSystemName());
//                deleteFile.delete();
//            }
        }
        return fileList;
    }
}
