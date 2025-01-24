package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.dto.ApiResult;
import com.aivle.fakedetecting.entity.Category;
import com.aivle.fakedetecting.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/category/{name}")
    @ResponseBody
    public Category createCategory(@PathVariable(name = "name") String name){
        // TODO: 관리자 검증 추가
        return categoryService.createCategory(name);
    }
    @DeleteMapping("/category/{name}")
    @ResponseBody
    public boolean deleteCategory(@PathVariable String name) throws Exception {
        // TODO: 관리자 검증 추가
        categoryService.deleteCategory(name);
        return true;
    }
    @GetMapping("/category")
    @ResponseBody
    public ApiResult<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.findAllCategory();
        return ApiResult.success(categories, "카테고리 성공");
    }
}
