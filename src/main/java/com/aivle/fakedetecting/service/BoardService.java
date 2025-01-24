package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.dto.RequestBoard;
import com.aivle.fakedetecting.dto.RequestBoardPassword;
import com.aivle.fakedetecting.entity.Board;
import com.aivle.fakedetecting.entity.Category;
import com.aivle.fakedetecting.entity.Image;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final ImageService imageService;
    @Transactional
    public Board createBoard(Long id, RequestBoard requestBoard, MultipartFile multipartFile) throws Exception {
        Member member = memberService.findMember(id);
        Category category = categoryService.findCategory(requestBoard.getCategory());
        Image image = imageService.uploadImage(multipartFile);
        Board board = Board.toEntity(requestBoard);
        board.setMember(member);
        board.setCategory(category);
        board.setImage(image);
        return boardRepository.save(board);
    }

    @Transactional
    public Board findBoard(RequestBoardPassword requestBoardPassword) throws Exception {
        Board board = boardRepository.findById(requestBoardPassword.getId())
                .orElseThrow(() -> new Exception("Board not found"));
        if(board.getPassword() != null && !board.getPassword().equals(requestBoardPassword.getPassword()))
            throw new Exception("Board password not match");
        return board;
    }

    public Board findBoardById(Long id) throws Exception {
        return boardRepository.findById(id)
                .orElseThrow(() -> new Exception("Board not found"));
    }

    public Page<Board> getPageBoards(int page){
        Pageable pageable = PageRequest.of(page, 10);
        return boardRepository.findAll(pageable);
    }
}
