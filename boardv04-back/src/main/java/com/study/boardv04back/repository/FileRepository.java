package com.study.boardv04back.repository;

import com.study.boardv04back.domain.File;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DB에 접근하여 File 클래스와 관련된 작업을 하는 interface
 */
@Mapper
@Repository
public interface FileRepository {

    /**
     * 입력받은 boardId를 가지는 모든 File return.
     *
     * @param boardId : return할 File의 boardId
     * @return 해당 boardId를 가지는 모든 File
     */
    List<File> getFileListByBoardId(int boardId);

    /**
     * 입력받은 fileId를 가지는 File을 return
     *
     * @param fileId : return할 File의 filedId
     * @return 해당 fileId를 가지는 File 인스턴스
     */
    Optional<File> getFileById(int fileId);

    /**
     * 입력받은 File을 insert
     *
     * @param file : insert할 File 인스턴스
     */
    void addFile(File file);

    /**
     * 입력받은 fileId를 가지는 File delete
     *
     * @param fileId : delete할 File의 fileId
     */
    void deleteFile(int fileId);

}
