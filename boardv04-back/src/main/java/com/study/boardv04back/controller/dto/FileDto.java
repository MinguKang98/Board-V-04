package com.study.boardv04back.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * POST /boards/{id}에 쓰이는 첨부파일 DTO
 */
@Getter
@Setter
public class FileDto {

    private MultipartFile file1;
    private MultipartFile file2;
    private MultipartFile file3;

    public List<MultipartFile> getFileList(){
        List<MultipartFile> fileList = new ArrayList<>();
        fileList.add(this.file1);
        fileList.add(this.file2);
        fileList.add(this.file3);
        return fileList;
    }

    public boolean isFileExist() {
        boolean result = false;
        List<MultipartFile> fileList = this.getFileList();
        for (MultipartFile file : fileList) {
            if (!file.isEmpty()) {
                result = true;
                break;
            }
        }
        return result;
    }

}
