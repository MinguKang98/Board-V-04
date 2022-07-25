package com.study.boardv04back.criteria;

import lombok.Builder;
import lombok.Getter;

/**
 * Board의 페이징 조건을 가지는 클래스
 */
@Getter
public class PagingCriteria {

    private int curPage = 1;
    private int rowSizePerPage = 10;
    private int pageSize = 10;
    private int totalRowCount;

    private int firstRow;
    private int lastRow;
    private int totalPageCount;
    private int firstPage;
    private int lastPage;

    @Builder
    public PagingCriteria(int curPage, int totalRowCount) {
        this.curPage = curPage;
        this.totalRowCount = totalRowCount;
        this.pageSetting();
    }

    public void pageSetting() {
        totalPageCount = (totalRowCount - 1) / rowSizePerPage + 1;

        firstRow = (curPage - 1) * rowSizePerPage;
        lastRow = firstRow + rowSizePerPage - 1;
        if (lastRow > totalRowCount) lastRow = totalRowCount - 1;

        firstPage = (curPage - 1) / pageSize * pageSize + 1;
        lastPage = firstPage + pageSize - 1;
        if (lastPage > totalPageCount) lastPage = totalPageCount;
    }

}
