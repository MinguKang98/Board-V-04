package com.study.boardv04back.repository;

import com.study.boardv04back.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DB에 접근하여 Comment 클래스와 관련된 작업을 하는 interface
 */
@Mapper
@Repository
public interface CommentRepository {

    /**
     * 입력받은 boardId를 가지는 모든 Comment return
     *
     * @param boardId : return할 Comment의 boardId
     * @return : 해당 boardId를 가지는 모든 Comment
     */
    List<Comment> getCommentListByBoardId(int boardId);

    /**
     * 입력받은 Comment insert
     *
     * @param comment : insert할 Comment 인스턴스
     */
    void addComment(Comment comment);

}