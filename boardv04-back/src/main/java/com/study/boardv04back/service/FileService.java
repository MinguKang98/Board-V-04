package com.study.boardv04back.service;

import com.study.boardv04back.domain.File;
import com.study.boardv04back.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * File 클래스와 관련된 로직을 처리하는 클래스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    /**
     * 입력받은 boardId를 가지는 모든 File return
     *
     * @param boardId : return할 File의 boardId
     * @return 해당 boardId를 가지는 모든 File
     */
    public List<File> getFileListByBoardId(int boardId) {

        List<File> fileList = fileRepository.getFileListByBoardId(boardId);
        return fileList;
    }

    /**
     * 입력받은 fileId를 가지는 File을 return
     *
     * @param fileId : return할 File의 fileId
     * @return 해당 fileId를 가지는 File 인스턴스
     */
    public File getFileById(int fileId) {

        File file = fileRepository.getFileById(fileId)
                .orElseThrow(IllegalStateException::new);
        return file;
    }

    /**
     * 입력받은 fileId를 가지는 File을 삭제한다
     *
     * @param fileId : 삭제할 File의 fileId
     */
    @Transactional
    public void deleteFile(int fileId) {

        fileRepository.deleteFile(fileId);
    }

}
