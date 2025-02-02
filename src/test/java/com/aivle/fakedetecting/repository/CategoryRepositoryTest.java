package com.aivle.fakedetecting.repository;

import com.aivle.fakedetecting.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(statements = "DELETE FROM category", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.yml")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should find category by name when it exists")
    void testFindByNameExists() {
        // Given
        Category category = new Category();
        category.setName("Electronics");
        categoryRepository.save(category);

        // When
        Optional<Category> foundCategory = categoryRepository.findByName("Electronics");

        // Then
        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getName()).isEqualTo("Electronics");
    }

    @Test
    @DisplayName("Should return empty Optional when category with name does not exist")
    void testFindByNameDoesNotExist() {
        // When
        Optional<Category> foundCategory = categoryRepository.findByName("NonExistent");

        // Then
        assertThat(foundCategory).isNotPresent();
    }

    @Test
    @DisplayName("Should handle case when multiple categories exist in database")
    void testFindByNameWithMultipleCategories() {
        // Given
        Category category1 = new Category();
        category1.setName("Books");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Music");
        categoryRepository.save(category2);

        // When
        Optional<Category> foundCategory = categoryRepository.findByName("Music");

        // Then
        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getName()).isEqualTo("Music");
    }
}