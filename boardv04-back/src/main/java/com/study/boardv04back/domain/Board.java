package com.study.boardv04back.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * 게시글을 나타내는 클래스
 */
@Getter
@Alias(value = "Board")
@NoArgsConstructor
public class Board {

    private int boardId;
    private String createdDate;
    private String updatedDate;
    private String user;
    private String password;
    private String title;
    private String content;
    private int visitCount;
    private int commentCount;
    private Boolean fileExist;
    private int categoryId;

    @Builder
    public Board(int boardId,
                 String user,
                 String password,
                 String title,
                 String content,
                 Boolean fileExist,
                 int categoryId) {

        this.boardId = boardId;
        this.user = user;
        this.password = password;
        this.title = title;
        this.content = content;
        this.fileExist = fileExist;
        this.categoryId = categoryId;
    }

}
