package com.study.boardv04back.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * 게시글의 카테고리를 나타내는 클래스
 */
@Getter
@Alias(value = "Category")
@NoArgsConstructor
public class Category {

    private int categoryId;
    private String name;

}
