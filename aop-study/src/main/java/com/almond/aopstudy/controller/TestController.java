package com.almond.aopstudy.controller;

import com.almond.aopstudy.myAnnotation.PaginationParam;
import com.almond.aopstudy.myAnnotation.PaginationResult;
import com.almond.aopstudy.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/list")
    public void getList(){
        PaginationParam paginationParam = new PaginationParam();
        paginationParam.setCountPerPage(5);
        paginationParam.setCurrentPageNumber(1);
        PaginationResult list = testService.getList(paginationParam);
        List data = list.getData();
        System.out.println(1);
    }
}
