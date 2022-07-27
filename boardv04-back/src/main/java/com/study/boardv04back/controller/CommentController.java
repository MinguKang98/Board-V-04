package com.study.boardv04back.controller;

import com.study.boardv04back.controller.dto.CommentDto;
import com.study.boardv04back.domain.Comment;
import com.study.boardv04back.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(value = "/boards/{boardId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable("boardId") int boardId) {

        List<Comment> commentList = commentService.getCommentListByBoardId(boardId);

        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @PostMapping(value = "/boards/{boardId}/comments")
    public ResponseEntity saveComment(@PathVariable("boardId") int boardId,
                                      @ModelAttribute CommentDto commentDto) {

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .boardId(boardId)
                .build();

        commentService.addComment(comment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
