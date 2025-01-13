package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.dto.RequestBoard;
import com.aivle.fakedetecting.dto.RequestBoardPassword;
import com.aivle.fakedetecting.entity.Board;
import com.aivle.fakedetecting.entity.Category;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final CategoryService categoryService;

    @Transactional
    public Board createBoard(Long id, RequestBoard requestBoard) throws Exception {
        Member member = memberService.findMember(id);
        Category category = categoryService.findCategory(requestBoard.getCategory());
        Board board = Board.toEntity(requestBoard);
        board.setMember(member);
        board.setCategory(category);
        return boardRepository.save(board);
    }

    @Transactional
    public Board findBoard(Long id, RequestBoardPassword requestBoardPassword) throws Exception {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new Exception("Board not found"));
        if(board.getPassword() != null && !board.getPassword().equals(requestBoardPassword.getPassword()))
            throw new Exception("Board password not match");
        return board;
    }

    // TODO: 게시판 목록 조회, Page<>
}
