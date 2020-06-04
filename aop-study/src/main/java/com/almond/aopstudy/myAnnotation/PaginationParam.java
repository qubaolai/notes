package com.almond.aopstudy.myAnnotation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * 分页参数
 * @author fangerhm
 */
@Getter
@NoArgsConstructor
public class PaginationParam {

    private Integer countPerPage;
    private Integer currentPageNumber;
    private String uniqueCode = "";

    public PaginationParam(Integer countPerPage, Integer currentPageNumber) {
        this.countPerPage = countPerPage;
        this.currentPageNumber = currentPageNumber;
        setUniqueCode();
    }

    public void setCountPerPage(Integer countPerPage) {
        this.countPerPage = countPerPage;
        setUniqueCode();
    }

    public void setCurrentPageNumber(Integer currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
        setUniqueCode();
    }

    private void setUniqueCode() {
        this.uniqueCode = StringUtils.EMPTY;
        if (this.countPerPage != null) {
            this.uniqueCode = this.uniqueCode + "-pp" + this.countPerPage;
        }
        if (this.currentPageNumber != null) {
            this.uniqueCode = this.uniqueCode + "-pn" + this.currentPageNumber;
        }
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<PaginationParam> paginationParamClass = PaginationParam.class;
        PaginationParam paginationParam = paginationParamClass.newInstance();
        paginationParam.setUniqueCode();
        paginationParam.setCountPerPage(1);
        System.out.println(1);
    }

}
