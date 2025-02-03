package com.aivle.fakedetecting.repository;

import com.aivle.fakedetecting.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
@TestPropertySource(locations = "classpath:application-test.yml")
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Should find a member by email when email exists in the database")
    void testFindByEmail_WhenEmailExists() {
        // Arrange
        Member member = Member.builder()
                .email("test@example.com")
                .name("John Doe")
                .nickName("JDoe")
                .password("password123")
                .passwordBefore("password456")
                .phone("123-456-7890")
                .svcAgmt(true)
                .infoAgmt(true)
                .build();
        memberRepository.save(member);

        // Act
        Optional<Member> retrievedMember = memberRepository.findByEmail("test@example.com");

        // Assert
        assertThat(retrievedMember).isPresent();
        assertThat(retrievedMember.get().getEmail()).isEqualTo("test@example.com");
        assertThat(retrievedMember.get().getName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Should return empty when trying to find a member by email that does not exist in the database")
    void testFindByEmail_WhenEmailDoesNotExist() {
        // Act
        Optional<Member> retrievedMember = memberRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertThat(retrievedMember).isNotPresent();
    }
}