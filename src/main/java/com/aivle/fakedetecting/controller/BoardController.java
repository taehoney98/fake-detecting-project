package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.config.jwt.MemberPrincipal;
import com.aivle.fakedetecting.dto.RequestBoard;
import com.aivle.fakedetecting.dto.RequestBoardPassword;
import com.aivle.fakedetecting.dto.ResponseBoard;
import com.aivle.fakedetecting.entity.Board;
import com.aivle.fakedetecting.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/board")
    @ResponseBody
    public Board createBoard(@RequestBody RequestBoard requestBoard, @AuthenticationPrincipal MemberPrincipal memberPrincipal) throws Exception {
        return boardService.createBoard(memberPrincipal.getUserId(), requestBoard);
    }

    @GetMapping("/board/{id}")
    @ResponseBody
    public ResponseBoard findBoard(@PathVariable Long id, @RequestBody RequestBoardPassword requestBoardPassword) throws Exception {
        Board board = boardService.findBoard(id, requestBoardPassword);
        return ResponseBoard.toDto(board);
    }
}
