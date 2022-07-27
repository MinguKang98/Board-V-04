package com.study.boardv04back.controller.dto;

import com.study.boardv04back.domain.Board;
import lombok.Getter;
import lombok.Setter;

/**
 * GET /boards에 쓰이는 DTO
 */
@Getter
@Setter
public class BoardListDto {

    private int boardId;
    private String createdDate;
    private String updatedDate;
    private String user;
    private String title;
    private int visitCount;
    private int commentCount;
    private Boolean fileExist;
    private int categoryId;

    public BoardListDto(Board board) {

        this.boardId = board.getBoardId();
        this.createdDate = board.getCreatedDate();
        this.updatedDate=board.getUpdatedDate();
        this.user=board.getUser();
        this.title=board.getTitle();
        this.visitCount=board.getVisitCount();
        this.commentCount=board.getCommentCount();
        this.fileExist=board.getFileExist();
        this.categoryId=board.getCategoryId();
    }

}
