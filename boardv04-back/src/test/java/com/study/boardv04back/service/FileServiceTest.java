package com.study.boardv04back.service;

import com.study.boardv04back.domain.File;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileServiceTest {

    @Autowired
    FileService fileService;

    @Test
    public void 게시글_첨부파일_모두_가져오기_정상() throws Exception {
        //given
        int boardId = 133;

        ///when
        List<File> fileList = fileService.getFileListByBoardId(boardId);

        //then
        assertThat(fileList.size()).isEqualTo(3);
    }

    @Test
    public void 게시글_첨부파일_모두_가져오기_empty() throws Exception {
        //given
        int boardId = 1;

        ///when
        List<File> fileList = fileService.getFileListByBoardId(boardId);

        //then
        assertThat(fileList).isEmpty();
    }

    @Test
    public void 파일_하나_가져오기_정상() throws Exception {
        //given
        int fileId = 35;

        ///when
        File file = fileService.getFileById(fileId);

        //then
        assertThat(file.getOriginName()).isEqualTo("erathosthenes.py");
    }

    @Test
    public void 파일_하나_가져오기_예외() throws Exception {
        //given
        int fileId = 1;

        ///when

        //then
        assertThrows(IllegalStateException.class, () -> {
            fileService.getFileById(fileId);
        });
    }

}