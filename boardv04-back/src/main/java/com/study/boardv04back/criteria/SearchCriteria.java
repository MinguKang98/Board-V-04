package com.study.boardv04back.criteria;

import lombok.Builder;
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

    @Builder
    public SearchCriteria(String createdDateFrom,
                          String createdDateTo,
                          Integer categoryId,
                          String text) {

        this.createdDateFrom = createdDateFrom;
        this.createdDateTo = createdDateTo;
        this.categoryId = categoryId;
        this.text = text;
    }

}
