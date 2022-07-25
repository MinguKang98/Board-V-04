package com.study.boardv04back.repository;

import com.study.boardv04back.domain.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DB에 접근하여 Category 클래스와 관련된 작업을 하는 interface
 */
@Mapper
@Repository
public interface CategoryRepository {

    /**
     * 모든 Category를 List 형태로 return
     *
     * @return : 모든 Category
     */
    List<Category> getCategoryList();

    /**
     * 입력받은 categoryId를 가지는 Category retrun
     *
     * @param categoryId : return할 Category의 categoryId
     * @return 해당 categoryId를 가지는 Category 인스턴스
     */
    Optional<Category> getCategoryById(int categoryId);

}