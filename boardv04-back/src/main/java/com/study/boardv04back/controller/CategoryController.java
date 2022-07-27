package com.study.boardv04back.controller;

import com.study.boardv04back.domain.Category;
import com.study.boardv04back.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Category 관련 api
 */
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 모든 Cateogry를 return
     *
     * @return 모든 Cateogry
     */
    @GetMapping(value = "/categories")
    public ResponseEntity<List<Category>> getCategories() {

        List<Category> categoryList = categoryService.getCategoryList();

        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    /**
     * cateogryId에 해당하는 Category return
     *
     * @param categoryId : return할 Cateogry의 categoryId
     * @return : 입력받은 categoryId를 가지는 Category
     */
    @GetMapping(value = "/categories/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable("categoryId") int categoryId) {

        Category category = categoryService.getCategoryById(categoryId);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

}
