package com.study.boardv04back.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * 게시글의 댓글을 나타내는 클래스
 */
@Getter
@Alias(value = "Comment")
@NoArgsConstructor
public class Comment {

    private int commentId;
    private String createdDate;
    private String content;
    private int boardId;

    @Builder
    public Comment(String content, int boardId) {
        this.content = content;
        this.boardId = boardId;
    }

}
