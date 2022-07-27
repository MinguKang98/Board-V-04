package com.study.boardv04back.util;

import com.study.boardv04back.domain.File;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * File 관련 메서드 제공 클래스
 */
@Component
public class FileUtils {

    @Value("${file.download-location}")
    private String DOWNLOAD_DIR;
    private String SEPARATOR = java.io.File.separator;

    /**
     * MultipartFile의 List를 입력받아 해당 파일을 저장하고 해당 MultiPartFile들을
     * File의 List로 변경 후 return
     *
     * @param multipartFileList : 다운로드하고 변환할 MultiPartFile의 List
     * @return : 추가할 File의 list
     * @throws IOException
     */
    public List<File> getAddFileList(List<MultipartFile> multipartFileList) throws IOException {

        List<File> fileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            if (multipartFile != null && !multipartFile.isEmpty()) {
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

    /**
     * MultipartFile의 List를 입력받아 해당 파일을 저장하고 해당 MultiPartFile들을
     * File의 List로 변경 후 return
     *
     * @param multipartFileList : 다운로드하고 변환할 MultiPartFile의 List
     * @param boardId           : File로 변환 시 추가할 File의 boardId
     * @return : 추가할 File의 list
     * @throws IOException
     */
    public List<File> getAddFileList(List<MultipartFile> multipartFileList, int boardId) throws IOException {

        List<File> fileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            if (multipartFile != null && !multipartFile.isEmpty()) {
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

    /**
     * File을 Resource로 변경
     *
     * @param file : 변경할 File
     * @return : 입력받은 File의 Resource
     * @throws IOException
     */
    public Resource getFileResource(File file) throws IOException {

        java.io.File downloadFile = new java.io.File(DOWNLOAD_DIR + SEPARATOR + file.getSystemName());
        InputStreamResource resource = new InputStreamResource((new FileInputStream(downloadFile)));

        return resource;
    }
}
