package com.study.boardv04back.controller.dto;

import com.study.boardv04back.domain.Board;
import lombok.Getter;
import lombok.Setter;

/**
 * GET /boards/{id}에 쓰이는 DTO
 */
@Getter
@Setter
public class BoardViewDto {

    private int boardId;
    private String createdDate;
    private String updatedDate;
    private String user;
    private String title;
    private String content;
    private int visitCount;
    private Boolean fileExist;
    private int categoryId;

    public BoardViewDto(Board board) {

        this.boardId = board.getBoardId();
        this.createdDate = board.getCreatedDate();
        this.updatedDate=board.getUpdatedDate();
        this.user=board.getUser();
        this.title=board.getTitle();
        this.content=board.getContent();
        this.visitCount=board.getVisitCount();
        this.fileExist=board.getFileExist();
        this.categoryId=board.getCategoryId();
    }

}
