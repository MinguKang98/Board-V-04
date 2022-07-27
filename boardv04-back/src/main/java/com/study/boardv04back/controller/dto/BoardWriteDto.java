package com.study.boardv04back.controller.dto;

import com.study.boardv04back.domain.Board;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * POST /boards/{id}에 쓰이는 DTO
 */
@Getter
@Setter
public class BoardWriteDto {

    @Size(min = 3, max = 4)
    private String user;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{4,15}$")
    private String password;

    @Size(min = 4, max = 99)
    private String title;

    @Size(min = 4, max = 1999)
    private String content;

    @Positive
    private int categoryId;

    public Board toBoard() {

        return Board.builder()
                .user(this.user)
                .password(this.password)
                .title(this.title)
                .content(this.content)
                .categoryId(this.categoryId)
                .build();
    }

}