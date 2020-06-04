package com.almond.aopstudy.myAnnotation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 分页查询结果
 * @author fangerhm
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResult {

    private PaginationParam paginationParam;
    private Integer count;
    private List data;

    public PaginationResult(List data) {
        this.data = data;
    }
}
