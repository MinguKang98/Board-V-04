package com.study.boardv04back.criteria;

import lombok.Builder;

/**
 * Board의 검색 조건, 가져올 첫 게시글 번호, 노출된느 게시글 갯수를 필드로 가지는 클래스.
 * getBoardListBySearchAndPagingCriteria 메서드의 파라미터로 쓰임
 */
public class SearchPagingCriteria {

    private String createdDateFrom;
    private String createdDateTo;
    private Integer categoryId;
    private String text;
    private int firstRow;
    private int rowSizePerPage;

    @Builder
    public SearchPagingCriteria(String createdDateFrom,
                                String createdDateTo,
                                Integer categoryId,
                                String text,
                                int firstRow,
                                int rowSizePerPage) {

        this.createdDateFrom = createdDateFrom;
        this.createdDateTo = createdDateTo;
        this.categoryId = categoryId;
        this.text = text;
        this.firstRow = firstRow;
        this.rowSizePerPage = rowSizePerPage;
    }

}
