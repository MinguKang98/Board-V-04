package com.study.boardv04back.service;

import com.study.boardv04back.domain.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;
    
    @Test
    public void 카테고리_모두_가져오기() throws Exception {
        //given

        ///when
        List<Category> categoryList = categoryService.getCategoryList();

        //then
        assertThat(categoryList.size()).isEqualTo(4);
    }

    @Test
    public void 카테고리_하나_가져오기_정상() throws Exception {
        //given
        int categoryId = 1;

        ///when
        Category category = categoryService.getCategoryById(categoryId);

        //then
        assertThat(category.getName()).isEqualTo("JAVA");
    }

    @Test
    public void 카테고리_하나_가져오기_예외() throws Exception {
        //given
        int categoryId = 10;

        ///when

        //then
        assertThrows(IllegalStateException.class, () -> {
            categoryService.getCategoryById(categoryId);
        });
    }

}