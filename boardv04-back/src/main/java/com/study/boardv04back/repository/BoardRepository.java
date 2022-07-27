package com.study.boardv04back.repository;

import com.study.boardv04back.criteria.SearchCriteria;
import com.study.boardv04back.criteria.SearchPagingCriteria;
import com.study.boardv04back.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DB에 접근하여 Board 클래스와 관련된 작업을 하는 interface
 */
@Mapper
@Repository
public interface BoardRepository {

    /**
     * 모든 Board를 List 형태로 return
     *
     * @return : 모든 Board
     */
    List<Board> getBoardList();

    /**
     * 입력받은 boardId를 가지는 Board return
     *
     * @param boardId : return할 Board 의 boardId
     * @return : 해당 bordId를 가지는 Board 인스턴스
     */
    Optional<Board> getBoardById(int boardId);

    /**
     * 검색 조건에 해당하는 Board의 총 갯수를 return
     *
     * @param searchCriteria : 검색 조건을 가지는 인스턴스
     * @return : 검색 조건에 해당하는 Board의 총 갯수
     */
    int getTotalBoardCountBySearchCriteria(SearchCriteria searchCriteria);

    /**
     * 검색 조건과 페이징 조건에 해당하는 Board를 List 형태로 return
     *
     * @param searchPagingCriteria : 검색 조건과 페이징 조건을 가지는 인스턴스
     * @return : 검색 조건과 페이징 조건을 만족하는 모든 Board
     */
    List<Board> getBoardListBySearchPagingCriteria(SearchPagingCriteria searchPagingCriteria);

    /**
     * 입력받은 Board insert
     *
     * @param board : insert 할 Board 인스턴스
     */
    void addBoard(Board board);

    /**
     * 입력받은 boardId를 가지는 Board delete
     *
     * @param boardId : delete Board의 boardId
     */
    void deleteBoard(int boardId);

    /**
     * 입력받은 Board update
     *
     * @param board : update 할 Board 인스턴스. boardId를 포함해야 함
     */
    void updateBoard(Board board);

    /**
     * 입력받은 boardId를 가지는 Board의 commentCount 1 증가
     *
     * @param boardId : update 할 Board의 boardId
     */
    void updateCommentCount(int boardId);

    /**
     * 입력받은 boardId를 가지는 Board의 visitCount 1 증가
     *
     * @param boardId : update 할 Board의 boardId
     */
    void updateVisitCount(int boardId);

}
