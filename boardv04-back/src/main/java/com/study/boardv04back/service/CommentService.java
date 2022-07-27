package com.study.boardv04back.service;

import com.study.boardv04back.domain.Comment;
import com.study.boardv04back.repository.BoardRepository;
import com.study.boardv04back.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Comment 클래스와 관련된 로직을 처리하는 클래스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    /**
     * 입력받은 boardId를 가지는 모든 Comment return
     *
     * @param boardId : return할 Comment의 boardId
     * @return 해당 boardId를 가지는 모든 Comment
     */
    public List<Comment> getCommentListByBoardId(int boardId) {

        List<Comment> commentList = commentRepository.getCommentListByBoardId(boardId);
        return commentList;
    }

    /**
     * 입력받은 Comment를 추가하고 해당 Baord의 commentCount를 update한다
     *
     * @param comment : 추가할 Comment 인스턴스
     */
    @Transactional
    public void addComment(Comment comment) {

        commentRepository.addComment(comment);

        boardRepository.updateCommentCount(comment.getBoardId());
    }

}
