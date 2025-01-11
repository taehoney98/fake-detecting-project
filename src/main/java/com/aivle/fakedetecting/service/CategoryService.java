package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.entity.Category;
import com.aivle.fakedetecting.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(String categoryName){
        Category category = new Category();
        category.setName(categoryName);
        return categoryRepository.save(category);
    }

    public Category findCategory(String categoryName) throws Exception {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new Exception("Category not found"));
    }

    public void deleteCategory(String categoryName) throws Exception {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new Exception("Category not found"));
        categoryRepository.delete(category);
    }

    public List<Category> findAllCategory(){
        return categoryRepository.findAll();
    }
}
