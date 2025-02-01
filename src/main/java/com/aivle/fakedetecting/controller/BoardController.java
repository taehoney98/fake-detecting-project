package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.config.jwt.MemberPrincipal;
import com.aivle.fakedetecting.dto.*;
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
    public ApiResult<Boolean> createBoard(@RequestPart(name = "requestBoard") RequestBoard requestBoard, @AuthenticationPrincipal MemberPrincipal memberPrincipal
            , @RequestPart(required = false, name = "multipartFile") MultipartFile multipartFile) throws Exception {
        boardService.createBoard(memberPrincipal.getUserId(), requestBoard, multipartFile);
        return ApiResult.success(true, "개시글 작성");
    }

    @PostMapping("/board-detail")
    @ResponseBody
    public ApiResult<ResponseBoard> findBoard(@RequestBody RequestBoardPassword requestBoardPassword) throws Exception {
        Board board = boardService.findBoard(requestBoardPassword);
        ResponseBoard responseBoard = ResponseBoard.toDto(board);
        if(board.getComment() != null)
            responseBoard.setComment(board.getComment().getContent());
        return ApiResult.success(responseBoard, "조회 성공");
    }

    @GetMapping("/board/list")
    @ResponseBody
    public ApiResult<Page<ResponseBoardPage>> boardPage(@Param("page") String page){
        int pageInteger = page.isEmpty() ? 0 : Integer.parseInt(page);
        Page<Board> board = boardService.getPageBoards(pageInteger);
        return ApiResult.success(board.map(ResponseBoardPage::toDto), "성공");
    }

    @PostMapping("/board-check")
    @ResponseBody
    public ApiResult<Boolean> checkBoard(@RequestBody RequestBoardPassword requestBoardPassword) throws Exception {
        boardService.BoardPasswordCheck(requestBoardPassword);
        return ApiResult.success(true, "비밀번호 일치");
    }

    @DeleteMapping("/board")
    @ResponseBody
    public ApiResult<Boolean> deleteBoard(@RequestBody RequestBoardPassword requestBoardPassword
            , @AuthenticationPrincipal MemberPrincipal memberPrincipal) throws Exception {
        boardService.deleteBoard(memberPrincipal.getUserId(), requestBoardPassword);
        return ApiResult.success(true, "삭제 성공");
    }
}
