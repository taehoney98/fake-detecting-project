package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.entity.Category;
import com.aivle.fakedetecting.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void createCategory_ValidInput_ShouldReturnSavedCategory() {
        // Arrange
        String categoryName = "Electronics";
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName(categoryName);

        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(mockCategory);

        // Act
        Category createdCategory = categoryService.createCategory(categoryName);

        // Assert
        assertThat(createdCategory).isNotNull();
        assertThat(createdCategory.getId()).isEqualTo(1L);
        assertThat(createdCategory.getName()).isEqualTo("Electronics");
    }

    @Test
    public void findCategory_ValidInput_ShouldReturnCategory() throws Exception {
        // Arrange
        String categoryName = "Electronics";
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName(categoryName);

        Mockito.when(categoryRepository.findByName(categoryName)).thenReturn(java.util.Optional.of(mockCategory));

        // Act
        Category foundCategory = categoryService.findCategory(categoryName);

        // Assert
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getId()).isEqualTo(1L);
        assertThat(foundCategory.getName()).isEqualTo("Electronics");
    }

    @Test
    public void findCategory_InvalidInput_ShouldThrowException() {
        // Arrange
        String categoryName = "NonExistingCategory";

        Mockito.when(categoryRepository.findByName(categoryName)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
            categoryService.findCategory(categoryName);
        });
    }
    @Test
    public void deleteCategory_ValidInput_ShouldDeleteCategory() throws Exception {
        // Arrange
        String categoryName = "Electronics";
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName(categoryName);

        Mockito.when(categoryRepository.findByName(categoryName)).thenReturn(java.util.Optional.of(mockCategory));
        Mockito.doNothing().when(categoryRepository).delete(mockCategory);

        // Act
        categoryService.deleteCategory(categoryName);

        // Assert
        Mockito.verify(categoryRepository, Mockito.times(1)).delete(mockCategory);
    }

    @Test
    public void deleteCategory_InvalidInput_ShouldThrowException() {
        // Arrange
        String categoryName = "NonExistingCategory";

        Mockito.when(categoryRepository.findByName(categoryName)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
            categoryService.deleteCategory(categoryName);
        });
    }

    @Test
    public void findAllCategory_WhenDataExists_ShouldReturnNonEmptyList() {
        // Arrange
        Category mockCategory1 = new Category();
        mockCategory1.setId(1L);
        mockCategory1.setName("Electronics");

        Category mockCategory2 = new Category();
        mockCategory2.setId(2L);
        mockCategory2.setName("Furniture");

        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(mockCategory1, mockCategory2));

        // Act
        List<Category> categories = categoryService.findAllCategory();

        // Assert
        assertThat(categories).isNotEmpty();
        assertThat(categories).hasSize(2);
        assertThat(categories.get(0).getName()).isEqualTo("Electronics");
        assertThat(categories.get(1).getName()).isEqualTo("Furniture");
    }

    @Test
    public void findAllCategory_WhenNoData_ShouldReturnEmptyList() {
        // Arrange
        Mockito.when(categoryRepository.findAll()).thenReturn(List.of());

        // Act
        List<Category> categories = categoryService.findAllCategory();

        // Assert
        assertThat(categories).isEmpty();
    }
}