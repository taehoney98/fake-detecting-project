package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.config.jwt.MemberPrincipal;
import com.aivle.fakedetecting.dto.RequestBoard;
import com.aivle.fakedetecting.dto.RequestBoardPassword;
import com.aivle.fakedetecting.dto.ResponseBoard;
import com.aivle.fakedetecting.dto.ResponseBoardPage;
import com.aivle.fakedetecting.entity.Board;
import com.aivle.fakedetecting.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/board")
    @ResponseBody
    public Board createBoard(@RequestPart RequestBoard requestBoard, @AuthenticationPrincipal MemberPrincipal memberPrincipal
            , @RequestPart(required = false) MultipartFile multipartFile) throws Exception {
        return boardService.createBoard(memberPrincipal.getUserId(), requestBoard, multipartFile);
    }

    @GetMapping("/board")
    @ResponseBody
    public ResponseBoard findBoard(@RequestBody RequestBoardPassword requestBoardPassword) throws Exception {
        Board board = boardService.findBoard(requestBoardPassword);
        ResponseBoard responseBoard = ResponseBoard.toDto(board);
        if(board.getComment() != null)
            responseBoard.setComment(board.getComment().getContent());
        return responseBoard;
    }

    @GetMapping("/board/list")
    @ResponseBody
    public Page<ResponseBoardPage> boardPage(@Param("page") Integer page){
        Page<Board> board = boardService.getPageBoards(page);
        return board.map(ResponseBoardPage::toDto);
    }
}
