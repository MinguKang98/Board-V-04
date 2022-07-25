package com.study.boardv04back.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    public void Board_Builder_Test_1() throws Exception {
        //given
        int boardId = 1;
        String user = "jane";
        String password = "123a!";
        String title = "good day";
        String content = "it's a good day";
        Boolean fileExist = false;
        int categoryId = 1;

        ///when
        Board board1 = Board.builder()
                .boardId(boardId)
                .user(user)
                .password(password)
                .title(title)
                .content(content)
                .fileExist(fileExist)
                .categoryId(categoryId)
                .build();

        //then
        assertThat(board1.getBoardId()).isEqualTo(boardId);
        assertThat(board1.getUser()).isEqualTo(user);
        assertThat(board1.getPassword()).isEqualTo(password);
        assertThat(board1.getTitle()).isEqualTo(title);
        assertThat(board1.getContent()).isEqualTo(content);
        assertThat(board1.getFileExist()).isEqualTo(fileExist);
    }

    @Test
    public void Board_Builder_Test_2() throws Exception {
        //given
        String user = "jane";
        String content = "it's a good day";

        ///when
        Board board2 = Board.builder()
                .user(user)
                .content(content)
                .build();

        //then
        assertThat(board2.getUser()).isEqualTo(user);
        assertThat(board2.getContent()).isEqualTo(content);

        assertThat(board2.getBoardId()).isZero();
        assertThat(board2.getPassword()).isNull();
        assertThat(board2.getTitle()).isNull();
        assertThat(board2.getFileExist()).isNull();

    }
}