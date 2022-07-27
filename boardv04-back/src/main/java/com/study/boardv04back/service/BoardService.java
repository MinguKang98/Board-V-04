package com.study.boardv04back.service;

import com.study.boardv04back.criteria.PagingCriteria;
import com.study.boardv04back.criteria.SearchCriteria;
import com.study.boardv04back.criteria.SearchPagingCriteria;
import com.study.boardv04back.domain.Board;
import com.study.boardv04back.domain.File;
import com.study.boardv04back.repository.BoardRepository;
import com.study.boardv04back.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Board 클래스와 관련된 로직을 처리하는 클래스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    /**
     * 검색 조건에 해당하는 Board의 총 갯수를 return
     *
     * @param searchCriteria : 검색 조건
     * @return : 검색 조건을 만족하는 Board의 총 갯수
     */
    public int getTotalBoardCountBySearchCriteria(SearchCriteria searchCriteria) {

        int totalBoardCountBySearchCriteria =
                boardRepository.getTotalBoardCountBySearchCriteria(searchCriteria);
        return totalBoardCountBySearchCriteria;
    }

    /**
     * 검색 조건과 페이징 조건을 만족하는 Board들을 return
     *
     * @param searchCriteria : 검색 조건
     * @param pagingCriteria : 페이징 조건
     * @return : 검색 조건과 페이징 조건을 만족하는 Board들
     */
    public List<Board> getBoardListBySearchCriteriaAndPagingCriteria(SearchCriteria searchCriteria,
                                                                     PagingCriteria pagingCriteria) {

        SearchPagingCriteria searchPagingCriteria = SearchPagingCriteria.builder()
                .createdDateFrom(searchCriteria.getCreatedDateFrom())
                .createdDateTo(searchCriteria.getCreatedDateTo())
                .categoryId(searchCriteria.getCategoryId())
                .text(searchCriteria.getText())
                .firstRow(pagingCriteria.getFirstRow())
                .rowSizePerPage(pagingCriteria.getRowSizePerPage())
                .build();

        List<Board> boardList = boardRepository.getBoardListBySearchPagingCriteria(searchPagingCriteria);

        return boardList;
    }

    /**
     * 입력받은 boardId를 가지는 Board를 return
     *
     * @param boardId : return할 Board의 boardId
     * @return 해당 boardId를 가지는 Board의 인스턴스
     */
    public Board getBoardById(int boardId) {

        Board board = boardRepository.getBoardById(boardId)
                .orElseThrow(IllegalStateException::new);
        return board;
    }

    /**
     * Board와 File들을 추가한다.
     *
     * @param board    : 추가할 Board
     * @param fileList : 추가할 File의 List
     */
    @Transactional
    public void addBoard(Board board, List<File> fileList) {

        boardRepository.addBoard(board);
        int boardId = board.getBoardId();

        for (File file : fileList) {
            fileRepository.addFile(File.builder()
                    .originName(file.getOriginName())
                    .systemName(file.getSystemName())
                    .boardId(boardId)
                    .build());
        }
    }

    /**
     * 입력받은 boardId를 가지는 Baord를 삭제한다.
     *
     * @param boardId : 삭제할 Board의 boardId
     */
    @Transactional
    public void deleteBoard(int boardId) {

        boardRepository.deleteBoard(boardId);
    }

    /**
     * 입력받은 Board를 수정하고, 변경된 File들을 추가 및 삭제한다.
     *
     * @param inputBoard     : 변경할 필드를 가진 Board
     * @param addFileList    : 추가할 File들의 List
     * @param deleteFileList : 삭제할 File들의 List
     */
    @Transactional
    public void updateBoard(Board inputBoard, List<File> addFileList, List<File> deleteFileList) {

        int boardId = inputBoard.getBoardId();

        for (File file : addFileList) {
            fileRepository.addFile(file);
        }

        for (File file : deleteFileList) {
            fileRepository.deleteFile(file.getFileId());
        }

        Board originBoard = boardRepository.getBoardById(boardId)
                .orElseThrow(IllegalStateException::new);

        String inputUser = inputBoard.getUser();
        String inputTitle = inputBoard.getTitle();
        String inputContent = inputBoard.getContent();
        boolean inputFileExist = (fileRepository.getFileListByBoardId(boardId).size() > 0);

        Board newBoard = originBoard.updateBoard(inputUser, inputTitle, inputContent, inputFileExist);

        boardRepository.updateBoard(newBoard);
    }

    /**
     * 입력받은 boardId를 가지는 Board의 visitCount를 1 중가시킨다.
     *
     * @param boardId : update할 Board의 boardId
     */
    @Transactional
    public void updateVisitCount(int boardId) {

        boardRepository.updateVisitCount(boardId);
    }

}

