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

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @GetMapping(value = "/boards")
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



    @PostMapping(value = "/boards")
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

        int boardId = board.getBoardId();
        System.out.println("boardId = " + boardId);

        return new ResponseEntity<>(boardId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/boards/count")
    public ResponseEntity<Integer> getTotalBoardCount(@ModelAttribute SearchCriteria searchCriteria) {

        int totalBoardCount = boardService.getTotalBoardCountBySearchCriteria(searchCriteria);

        return new ResponseEntity<>(totalBoardCount, HttpStatus.OK);
    }

    @GetMapping(value = "/boards/{boardId}")
    public ResponseEntity<BoardViewDto> getBoard(@PathVariable("boardId") int boardId) {

        boardService.updateVisitCount(boardId);

        Board board = boardService.getBoardById(boardId);
        BoardViewDto boardViewDto = new BoardViewDto(board);

        return new ResponseEntity<>(boardViewDto, HttpStatus.OK);
    }


    @DeleteMapping(value = "/boards/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable("boardId") int boardId) {

        boardService.deleteBoard(boardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/boards/{boardId}")
    public ResponseEntity updateBoard(
            @PathVariable("boardId") int boardId,
            @ModelAttribute @Valid BoardModifyDto boardModifyDto,
            @ModelAttribute FileDto fileDto,
            @RequestParam(value = "deleteFileList") List<Integer> deleteFileListByFileId,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Map<String, String> resultMap = new HashMap<>();

        String originPassword = boardService.getBoardById(boardId).getPassword();
        String inputPassword = boardModifyDto.getPassword();
        if (!originPassword.equals(inputPassword)) {
            resultMap.put("result", "fail");
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }

        Board board = boardModifyDto.toBoard();
        board.setBoardId(boardId);
        List<File> addFileList;
        if (fileDto.getFileList() == null) {
            addFileList = new ArrayList<>();
        }else{
            addFileList = fileUtils.getAddFileList(fileDto.getFileList(), boardId);
        }
        List<File> deleteFileList = deleteFileListByFileId.stream()
                .map(i -> fileService.getFileById(i))
                .collect(Collectors.toList());

        boardService.updateBoard(board, addFileList, deleteFileList);

        resultMap.put("result", "success");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PostMapping(value = "/boards/{boardId}/password-check")
    public ResponseEntity<Map<String, String>> passwordCheck(
            @PathVariable("boardId") int boardId,
            @RequestParam("password") String inputPassword) {

        Board originBoard = boardService.getBoardById(boardId);

        Map<String, String> resultMap = new HashMap<>();
        if (originBoard.getPassword().equals(inputPassword)) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
