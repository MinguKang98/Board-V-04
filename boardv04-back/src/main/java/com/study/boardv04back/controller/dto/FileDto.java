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

    private List<MultipartFile> fileList;

    public boolean isFileExist() {
        if (fileList == null) {
            return false;
        }

        boolean result = false;
        for (MultipartFile file : fileList) {
            if (!file.isEmpty()) {
                result = true;
                break;
            }
        }
        return result;
    }

}
