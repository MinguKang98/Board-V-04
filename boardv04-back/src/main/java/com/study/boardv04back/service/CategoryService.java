package com.study.boardv04back.service;

import com.study.boardv04back.domain.Category;
import com.study.boardv04back.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Category와 관련된 로직을 처리하는 클래스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     *  모든 Category를 List 형태로 return
     *
     * @return 모든 Category
     */
    public List<Category> getCategoryList() {

        List<Category> categoryList = categoryRepository.getCategoryList();
        return categoryList;
    }

    /**
     * 입력받은 categoryId를 가지는 Category return
     *
     * @param categoryId : return할 Category의 categoryId
     * @return 해당 categoryId를 가지는 Category 인스턴스, 인스턴스가 존재하지 않는다면 null
     */
    public Category getCategoryById(int categoryId){

        Category category = categoryRepository.getCategoryById(categoryId)
                .orElseThrow(IllegalStateException::new);
        return category;
    }

}
