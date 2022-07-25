package com.study.boardv04back.domain;

import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

/**
 * 게시글의 첨부 파일을 나타내는 클래스
 */
@Getter
@Alias(value = "File")
public class File {

    int fileId;
    String originName;
    String systemName;
    int boardId;

    @Builder
    public File(String originName, String systemName, int boardId) {
        this.originName = originName;
        this.systemName = systemName;
        this.boardId = boardId;
    }

}
