package com.aivle.fakedetecting.repository;

import com.aivle.fakedetecting.entity.Board;
import com.aivle.fakedetecting.entity.Category;
import com.aivle.fakedetecting.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository; // Assuming you have a MemberRepository
    @Autowired
    private CategoryRepository categoryRepository; // Assuming you have a CategoryRepository

    @Test
    @DisplayName("Test findAll - Should return an empty page when no boards exist")
    public void testFindAll_EmptyPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Board> result = boardRepository.findAll(pageable);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Test findAll - Should return all persisted boards")
    public void testFindAll_WithData() {
        Member member = memberRepository.save(Member.builder().build());
        Category category = categoryRepository.save(new Category());
        ; // Assuming Category attributes and builder exist

        Board board1 = Board.builder()
                .title("Title 1")
                .content("Content 1")
                .password("password1")
                .member(member)
                .category(category)
                .build();

        Board board2 = Board.builder()
                .title("Title 2")
                .content("Content 2")
                .password("password2")
                .member(member)
                .category(category)
                .build();

        boardRepository.saveAll(List.of(board1, board2));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Board> result = boardRepository.findAll(pageable);

        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).extracting(Board::getTitle).containsExactlyInAnyOrder("Title 1", "Title 2");
    }
}