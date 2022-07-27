package com.study.boardv04back.controller.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * POST /boards/{id}/comments에 쓰이는 DTO
 */
@Getter
@Setter
public class CommentDto {

    private String content;

}
