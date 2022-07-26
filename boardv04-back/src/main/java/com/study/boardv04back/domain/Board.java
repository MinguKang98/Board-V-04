package com.study.boardv04back.domain;

import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 게시글을 나타내는 클래스
 */
@Getter
@Setter(value = AccessLevel.PRIVATE)
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

    /**
     *
     * @param inputUser
     * @param inputTitle
     * @param inputContent
     * @param inputFileExist
     * @return
     */
    public Board updateBoard(String inputUser,
                             String inputTitle,
                             String inputContent,
                             Boolean inputFileExist) {

        int boardId = this.getBoardId();
        String originUser = this.getUser();
        String originTitle = this.getTitle();
        String originContent = this.getContent();
        Boolean originFileExist = this.getFileExist();


        Board newBoard = new Board();
        newBoard.setBoardId(boardId);
        if (!originUser.equals(inputUser)) {
            newBoard.setUser(inputUser);
        }
        if (!originTitle.equals(inputTitle)) {
            newBoard.setTitle(inputTitle);
        }
        if (!originContent.equals(inputContent)) {
            newBoard.setContent(inputContent);
        }
        if (originFileExist != inputFileExist) {
            newBoard.setFileExist(inputFileExist);
        }

        return newBoard;
    }

}
