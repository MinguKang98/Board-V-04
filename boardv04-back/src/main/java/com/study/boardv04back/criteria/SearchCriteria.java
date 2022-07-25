package com.study.boardv04back.criteria;

import lombok.Getter;

/**
 * Board의 검색 조건을 가지는 클래스
 */
@Getter
public class SearchCriteria {

    private String createdDateFrom;
    private String createdDateTo;
    private Integer categoryId;
    private String text;

}
