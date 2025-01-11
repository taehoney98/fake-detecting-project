package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.dto.RequestBoard;
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
}
