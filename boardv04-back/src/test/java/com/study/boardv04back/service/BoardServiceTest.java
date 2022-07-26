package com.study.boardv04back.service;

import com.study.boardv04back.criteria.PagingCriteria;
import com.study.boardv04back.criteria.SearchCriteria;
import com.study.boardv04back.domain.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardService boardService;
    
    @Test
    public void 게시글_총_갯수_가져오기1() throws Exception {
        //given
        SearchCriteria searchCriteria = SearchCriteria.builder()
                .createdDateFrom("2022-07-10")
                .createdDateTo("2022-07-20")
                .categoryId(2)
                .text("테스트")
                .build();

        ///when
        int totalBoardCount = boardService.getTotalBoardCountBySearchCriteria(searchCriteria);

        //then
        assertThat(totalBoardCount).isEqualTo(31);
    }

    @Test
    public void 게시글_총_갯수_가져오기2() throws Exception {
        //given
        SearchCriteria searchCriteria = SearchCriteria.builder()
                .categoryId(2)
                .build();

        ///when
        int totalBoardCount = boardService.getTotalBoardCountBySearchCriteria(searchCriteria);

        //then
        assertThat(totalBoardCount).isEqualTo(34);
    }

    @Test
    public void 게시글_모두_가져오기_1() throws Exception {
        //given
        SearchCriteria searchCriteria = SearchCriteria.builder()
                .createdDateFrom("2022-07-10")
                .createdDateTo("2022-07-20")
                .categoryId(2)
                .text("테스트")
                .build();

        int totalBoardCount = boardService.getTotalBoardCountBySearchCriteria(searchCriteria);
        PagingCriteria pagingCriteria = PagingCriteria.builder()
                .curPage(3)
                .totalRowCount(totalBoardCount)
                .build();

        ///when
        List<Board> boardList = boardService
                .getBoardListBySearchCriteriaAndPagingCriteria(searchCriteria,pagingCriteria);

        //then
        assertThat(boardList.size()).isEqualTo(10);
        assertThat(boardList.get(0).getBoardId()).isEqualTo(50);
        assertThat(boardList.get(9).getBoardId()).isEqualTo(40);
    }

    @Test
    public void 게시글_하나_가져오기_정상() throws Exception {
        //given
        int boardId = 140;

        ///when
        Board board = boardService.getBoardById(boardId);

        //then
        assertThat(board.getTitle()).isEqualTo("file test");
    }

    @Test
    public void 게시글_하나_가져오기_예외() throws Exception {
        //given
        int boardId = 233;

        ///when

        //then
        assertThrows(IllegalStateException.class, () -> {
            boardService.getBoardById(boardId);
        });
    }

}