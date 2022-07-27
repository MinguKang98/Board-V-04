package com.study.boardv04back.controller;

import com.study.boardv04back.controller.dto.*;
import com.study.boardv04back.criteria.PagingCriteria;
import com.study.boardv04back.criteria.SearchCriteria;
import com.study.boardv04back.domain.Board;
import com.study.boardv04back.domain.File;
import com.study.boardv04back.service.BoardService;
import com.study.boardv04back.service.FileService;
import com.study.boardv04back.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestController
@RequestMapping(value = "/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;
    private final FileUtils fileUtils;

    /**
     * @param curPage        : 현재 페이지
     * @param searchCriteria : 검색 조건
     * @return
     */
    @GetMapping(value = "")
    public ResponseEntity<List<BoardListDto>> getBoards(
            @RequestParam(value = "curPage", defaultValue = "1") int curPage,
            @ModelAttribute SearchCriteria searchCriteria) {

        int totalBoardCount = boardService.getTotalBoardCountBySearchCriteria(searchCriteria);
        PagingCriteria pagingCriteria = new PagingCriteria(curPage, totalBoardCount);

        List<Board> boardList = boardService.getBoardListBySearchCriteriaAndPagingCriteria(
                searchCriteria, pagingCriteria);

        List<BoardListDto> result = boardList.stream()
                .map(b -> new BoardListDto(b))
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity saveBoard(@ModelAttribute FileDto fileDto,
                                    @ModelAttribute @Valid BoardWriteDto boardWriteDto,
                                    BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Board board = boardWriteDto.toBoard();
        board.setFileExist(fileDto.isFileExist());

        List<MultipartFile> multipartFileList = fileDto.getFileList();
        List<File> addFileList = fileUtils.getAddFileList(multipartFileList);

        boardService.addBoard(board, addFileList);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Integer> getTotalBoardCount(
            @RequestParam(value = "curPage", defaultValue = "1") int curPage,
            @ModelAttribute SearchCriteria searchCriteria) {

        int totalBoardCount = boardService.getTotalBoardCountBySearchCriteria(searchCriteria);

        return new ResponseEntity<>(totalBoardCount, HttpStatus.OK);
    }

    @GetMapping(value = "/{boardId}")
    public ResponseEntity<BoardViewDto> getBoard(
            @PathVariable("boardId") int boardId,
            @RequestParam(value = "curPage", defaultValue = "1") int curPage,
            @ModelAttribute SearchCriteria searchCriteria) {

        boardService.updateVisitCount(boardId);

        Board board = boardService.getBoardById(boardId);
        BoardViewDto boardViewDto = new BoardViewDto(board);

        return new ResponseEntity<>(boardViewDto, HttpStatus.OK);
    }


    @DeleteMapping(value = "/{boardId}")
    public ResponseEntity deleteBoard(
            @PathVariable("boardId") int boardId,
            @RequestParam(value = "curPage", defaultValue = "1") int curPage,
            @ModelAttribute SearchCriteria searchCriteria) {

        boardService.deleteBoard(boardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{boardId}")
    public ResponseEntity updateBoard(
            @PathVariable("boardId") int boardId,
            @RequestParam(value = "curPage", defaultValue = "1") int curPage,
            @ModelAttribute SearchCriteria searchCriteria,
            @ModelAttribute @Valid BoardModifyDto boardModifyDto,
            @ModelAttribute FileDto fileDto,
            MultipartHttpServletRequest request,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String originPassword = boardService.getBoardById(boardId).getPassword();
        String inputPassword = boardModifyDto.getPassword();
        if (!originPassword.equals(inputPassword)) {

        }

        // TODO updateBoard 다시 구성
        Board board = boardModifyDto.toBoard();
        board.setBoardId(boardId);
        List<File> originFileList = fileService.getFileListByBoardId(boardId);
        List<MultipartFile> multipartFileList = fileDto.getFileList();

        List<File> deleteFileList = fileUtils.getDeleteFileList(originFileList);
        List<File> addFileList = fileUtils.getAddFileList(multipartFileList, boardId);

        boardService.updateBoard(board, addFileList, deleteFileList);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
