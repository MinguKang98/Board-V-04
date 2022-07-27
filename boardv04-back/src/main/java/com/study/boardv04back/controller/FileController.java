package com.study.boardv04back.controller;

import com.study.boardv04back.domain.Comment;
import com.study.boardv04back.domain.File;
import com.study.boardv04back.service.FileService;
import com.study.boardv04back.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * File 관련 api
 */
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FileUtils fileUtils;

    /**
     * boardId에 해당하는 모든 File return
     *
     * @param boardId : return할 File의 boardId
     * @return : 입력받은 boardId에 해당하는 File
     */
    @GetMapping(value = "/boards/{boardId}/files")
    public ResponseEntity<List<File>> getFiles(@PathVariable("boardId") int boardId) {

        List<File> fileList = fileService.getFileListByBoardId(boardId);

        return new ResponseEntity<>(fileList, HttpStatus.OK);
    }


    /**
     * fileId에 해당하는 File 다운로드
     *
     * @param fileId : 다운로드 할 File의 fileId
     * @return : ResponseEntity
     * @throws IOException
     */
    @GetMapping(value = "/files/{fileId}")
    public ResponseEntity downloadFile(@PathVariable("fileId") int fileId) throws IOException {

        File file = fileService.getFileById(fileId);
        String fileName = new String(file.getOriginName().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

        Resource fileResource = fileUtils.getFileResource(file);

        //FIXME 깨짐 현상 수정

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileResource);
    }

}
