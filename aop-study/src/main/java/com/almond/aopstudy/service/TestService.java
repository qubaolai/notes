package com.almond.aopstudy.service;

import com.almond.aopstudy.myAnnotation.PageResolver;
import com.almond.aopstudy.myAnnotation.PaginationParam;
import com.almond.aopstudy.myAnnotation.PaginationResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @PageResolver(countPerPage = "#{paginationParam.countPerPage}", currentPageNumber = "#{paginationParam.currentPageNumber}")
    public PaginationResult getList(PaginationParam paginationParam){
        List<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王伟");
        list.add("张磊");
        list.add("刘亮");
        list.add("文欢");
        list.add("徐晓");
        list.add("蔡雯");
        PaginationResult paginationResult = new PaginationResult(list);
        return paginationResult;
    }


}
