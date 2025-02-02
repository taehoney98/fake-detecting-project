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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BoardServiceTest {
    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ImageService imageService;

    @Mock
    private MultipartFile multipartFile;

    @Test
    void createBoard_ShouldReturnBoard_WhenAllServicesAndRepositoryAreValid() throws Exception {
        Long memberId = 1L;
        RequestBoard requestBoard = new RequestBoard();
        Member mockMember = new Member();
        mockMember.setSeq(memberId);
        Category mockCategory = new Category();
        Image mockImage = new Image();
        Board mockBoard = new Board();

        when(memberService.findMember(memberId)).thenReturn(mockMember);
        when(categoryService.findCategory(any())).thenReturn(mockCategory);
        when(imageService.uploadImage(multipartFile)).thenReturn(mockImage);
        when(boardRepository.save(any(Board.class))).thenReturn(mockBoard);

        Board result = boardService.createBoard(memberId, requestBoard, multipartFile);

        assertNotNull(result);
        verify(memberService, times(1)).findMember(memberId);
        verify(categoryService, times(1)).findCategory(any());
        verify(imageService, times(1)).uploadImage(multipartFile);
        verify(boardRepository, times(1)).save(any(Board.class));
    }

    @Test
    void createBoard_ShouldThrowException_WhenMemberServiceFails() throws Exception {
        Long memberId = 1L;
        RequestBoard requestBoard = new RequestBoard();

        when(memberService.findMember(memberId)).thenThrow(new RuntimeException("Member not found"));

        assertThrows(Exception.class, () -> boardService.createBoard(memberId, requestBoard, multipartFile));

        verify(memberService, times(1)).findMember(memberId);
        verify(categoryService, times(0)).findCategory(any());
        verify(imageService, times(0)).uploadImage(any());
        verify(boardRepository, times(0)).save(any(Board.class));
    }

    @Test
    void createBoard_ShouldThrowException_WhenCategoryServiceFails() throws Exception {
        Long memberId = 1L;
        RequestBoard requestBoard = new RequestBoard();
        Member mockMember = new Member();
        mockMember.setSeq(memberId);

        when(memberService.findMember(memberId)).thenReturn(mockMember);
        when(categoryService.findCategory(any())).thenThrow(new Exception("Category not found"));

        assertThrows(Exception.class, () -> boardService.createBoard(memberId, requestBoard, multipartFile));

        verify(memberService, times(1)).findMember(memberId);
        verify(categoryService, times(1)).findCategory(any());
        verify(imageService, times(0)).uploadImage(any());
        verify(boardRepository, times(0)).save(any(Board.class));
    }

    @Test
    void createBoard_ShouldThrowException_WhenImageServiceFails() throws Exception {
        Long memberId = 1L;
        RequestBoard requestBoard = new RequestBoard();
        Member mockMember = new Member();
        mockMember.setSeq(memberId);
        Category mockCategory = new Category();

        when(memberService.findMember(memberId)).thenReturn(mockMember);
        when(categoryService.findCategory(any())).thenReturn(mockCategory);
        when(imageService.uploadImage(multipartFile)).thenThrow(new RuntimeException("Image upload failed"));

        assertThrows(Exception.class, () -> boardService.createBoard(memberId, requestBoard, multipartFile));

        verify(memberService, times(1)).findMember(memberId);
        verify(categoryService, times(1)).findCategory(any());
        verify(imageService, times(1)).uploadImage(multipartFile);
        verify(boardRepository, times(0)).save(any(Board.class));
    }

    @Test
    void findBoard_ShouldReturnBoard_WhenRoleIsAdmin() throws Exception {
        RequestBoardPassword requestBoardPassword = new RequestBoardPassword();
        requestBoardPassword.setId(1L);
        MemberPrincipal mockAdmin = mock(MemberPrincipal.class);
        when(mockAdmin.getRole()).thenReturn(Role.ROLE_ADMIN);
        Board mockBoard = new Board();
        when(boardRepository.findById(1L)).thenReturn(Optional.of(mockBoard));

        Board result = boardService.findBoard(requestBoardPassword, mockAdmin);

        assertNotNull(result);
        verify(boardRepository, times(1)).findById(1L);
    }

    @Test
    void findBoard_ShouldReturnBoard_WhenPasswordMatches() throws Exception {
        RequestBoardPassword requestBoardPassword = new RequestBoardPassword();
        requestBoardPassword.setId(1L);
        requestBoardPassword.setPassword("validPassword");
        MemberPrincipal mockUser = mock(MemberPrincipal.class);
        when(mockUser.getRole()).thenReturn(Role.ROLE_USER);
        Board mockBoard = new Board();
        mockBoard.setPassword("validPassword");
        when(boardRepository.findById(1L)).thenReturn(Optional.of(mockBoard));

        Board result = boardService.findBoard(requestBoardPassword, mockUser);

        assertNotNull(result);
        verify(boardRepository, times(1)).findById(1L);
    }

    @Test
    void findBoard_ShouldThrowException_WhenBoardDoesNotExist() {
        RequestBoardPassword requestBoardPassword = new RequestBoardPassword();
        requestBoardPassword.setId(1L);
        MemberPrincipal mockUser = mock(MemberPrincipal.class);
        when(boardRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> boardService.findBoard(requestBoardPassword, mockUser));

        verify(boardRepository, times(1)).findById(1L);
    }

    @Test
    void findBoard_ShouldThrowException_WhenPasswordIsIncorrect() {
        RequestBoardPassword requestBoardPassword = new RequestBoardPassword();
        requestBoardPassword.setId(1L);
        requestBoardPassword.setPassword("wrongPassword");
        MemberPrincipal mockUser = mock(MemberPrincipal.class);
        when(mockUser.getRole()).thenReturn(Role.ROLE_USER);
        Board mockBoard = new Board();
        mockBoard.setPassword("validPassword");
        when(boardRepository.findById(1L)).thenReturn(Optional.of(mockBoard));

        assertThrows(Exception.class, () -> boardService.findBoard(requestBoardPassword, mockUser));

        verify(boardRepository, times(1)).findById(1L);
    }

    @Test
    void getPageBoards_ShouldReturnPopulatedPage_WhenBoardsExist() {
        int page = 0;
        Page<Board> mockPage = mock(Page.class);
        when(boardRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<Board> result = boardService.getPageBoards(page);

        assertNotNull(result);
        verify(boardRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getPageBoards_ShouldReturnEmptyPage_WhenNoBoardsExist() {
        int page = 0;
        Page<Board> emptyPage = Page.empty();
        when(boardRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        Page<Board> result = boardService.getPageBoards(page);

        assertEquals(0, result.getTotalElements());
        verify(boardRepository, times(1)).findAll(any(Pageable.class));
    }


    @Test
    void BoardPasswordCheck_ShouldReturnBoard_WhenPasswordMatches() throws Exception {
        RequestBoardPassword requestBoardPassword = new RequestBoardPassword();
        requestBoardPassword.setId(1L);
        requestBoardPassword.setPassword("validPassword");
        Board mockBoard = new Board();
        mockBoard.setPassword("validPassword");

        when(boardRepository.findById(1L)).thenReturn(Optional.of(mockBoard));

        Board result = boardService.BoardPasswordCheck(requestBoardPassword);

        assertNotNull(result);
        verify(boardRepository, times(1)).findById(1L);
    }

    @Test
    void BoardPasswordCheck_ShouldThrowException_WhenPasswordDoesNotMatch() throws Exception {
        RequestBoardPassword requestBoardPassword = new RequestBoardPassword();
        requestBoardPassword.setId(1L);
        requestBoardPassword.setPassword("wrongPassword");
        Board mockBoard = new Board();
        mockBoard.setPassword("validPassword");

        when(boardRepository.findById(1L)).thenReturn(Optional.of(mockBoard));

        assertThrows(Exception.class, () -> boardService.BoardPasswordCheck(requestBoardPassword));

        verify(boardRepository, times(1)).findById(1L);
    }

    @Test
    void putBoard_ShouldModifyBoardSuccessfully_WhenValidInputsProvided() throws Exception {
        Long boardId = 1L;
        RequestBoard requestBoard = new RequestBoard();
        Category mockCategory = new Category();
        Board existingBoard = new Board();

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(existingBoard));
        when(categoryService.findCategory(requestBoard.getCategory())).thenReturn(mockCategory);

        Board result = boardService.putBoard(boardId, requestBoard);

        assertNotNull(result);
        verify(boardRepository, times(1)).findById(boardId);
        verify(categoryService, times(1)).findCategory(requestBoard.getCategory());
    }

    @Test
    void putBoard_ShouldThrowException_WhenBoardDoesNotExist() throws Exception {
        Long boardId = 1L;
        RequestBoard requestBoard = new RequestBoard();

        when(boardRepository.findById(boardId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> boardService.putBoard(boardId, requestBoard));

        verify(boardRepository, times(1)).findById(boardId);
        verify(categoryService, times(0)).findCategory(any());
    }

    @Test
    void putBoard_ShouldThrowException_WhenCategoryDoesNotExist() throws Exception {
        Long boardId = 1L;
        RequestBoard requestBoard = new RequestBoard();
        Board existingBoard = new Board();

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(existingBoard));
        when(categoryService.findCategory(requestBoard.getCategory())).thenThrow(new Exception("Category not found"));

        assertThrows(Exception.class, () -> boardService.putBoard(boardId, requestBoard));

        verify(boardRepository, times(1)).findById(boardId);
        verify(categoryService, times(1)).findCategory(requestBoard.getCategory());
    }
}