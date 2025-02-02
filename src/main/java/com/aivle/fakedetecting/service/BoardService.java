package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.config.jwt.MemberPrincipal;
import com.aivle.fakedetecting.dto.RequestBoard;
import com.aivle.fakedetecting.dto.RequestBoardPassword;
import com.aivle.fakedetecting.entity.Board;
import com.aivle.fakedetecting.entity.Category;
import com.aivle.fakedetecting.entity.Image;
import com.aivle.fakedetecting.entity.Member;
import com.aivle.fakedetecting.enums.Role;
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
        if(image != null) {
            board.setImage(image);
            image.setBoard(board);
        }
        return boardRepository.save(board);
    }

    @Transactional
    public Board findBoard(RequestBoardPassword requestBoardPassword, MemberPrincipal memberPrincipal) throws Exception {
        Board board = boardRepository.findById(requestBoardPassword.getId())
                .orElseThrow(() -> new Exception("게시글이 없습니다."));
        if(memberPrincipal != null && memberPrincipal.getRole().equals(Role.ROLE_ADMIN)){
            return board;
        }
        if(board.getPassword() != null && !board.getPassword().equals(requestBoardPassword.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");
        return board;
    }

    public Board findBoardById(Long id) throws Exception {
        return boardRepository.findById(id)
                .orElseThrow(() -> new Exception("게시글이 없습니다."));
    }

    public Page<Board> getPageBoards(int page){
        Pageable pageable = PageRequest.of(page, 8);
        return boardRepository.findAllByOrderByIdDesc(pageable);
    }

    public Board BoardPasswordCheck(RequestBoardPassword requestBoardPassword) throws Exception {
        Board board = findBoardById(requestBoardPassword.getId());
        if(!board.getPassword().equals(requestBoardPassword.getPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        return board;
    }

    @Transactional
    public void deleteBoard(Long memberId, RequestBoardPassword requestBoardPassword) throws Exception {
        Board board = findBoardById(requestBoardPassword.getId());
        if(board.getPassword() != null && !board.getPassword().equals(requestBoardPassword.getPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        if (board.getMember().getSeq() != memberId){
            throw new Exception("삭제할 수 없습니다.");
        }
        boardRepository.delete(board);
    }

    @Transactional
    public Board putBoard(Long id, RequestBoard requestBoard) throws Exception {
        Board board = findBoardById(id);
        board.modify(requestBoard);
        Category category = categoryService.findCategory(requestBoard.getCategory());
        board.setCategory(category);
        return board;
    }
}
