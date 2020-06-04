package com.almond.aopstudy.myAnnotation;

import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 分页注解解析
 * @author fangerhm
 */
@Component
@Slf4j
@Aspect
public class PaginationParse {

    @Pointcut(value = "@annotation(com.almond.aopstudy.myAnnotation.PageResolver)")
    public void pointCut(){}

    @Around(value = "pointCut() && @annotation(pageResolver)")
    public Object around(ProceedingJoinPoint pjp, PageResolver pageResolver){
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            log.error("around error",throwable);
            return null;
        }
        if (!(result instanceof PaginationResult)){
            return result;
        }
        int countPerPage= Integer.valueOf(Optional.fromNullable(AnnotationResolver.newInstance().resolver(pjp,pageResolver.countPerPage())).or("0").toString());
        int currentPageNumber= Integer.valueOf(Optional.fromNullable(AnnotationResolver.newInstance().resolver(pjp,pageResolver.currentPageNumber())).or("0").toString());
        if(countPerPage==0||currentPageNumber==0){
            return result;
        }
        PaginationResult paginationResult= (PaginationResult) result;
        List data = paginationResult.getData();
        int dataCount = data.size();

        int maxPageNumber= dataCount /countPerPage + 1;
        if (currentPageNumber > maxPageNumber){
            currentPageNumber = maxPageNumber;
        }

        int minIndex = countPerPage * (currentPageNumber - 1);
        int maxIndex = Math.min(minIndex + countPerPage, dataCount);
        data = data.subList(minIndex,maxIndex);

        paginationResult.setPaginationParam(new PaginationParam(countPerPage,currentPageNumber));
        paginationResult.setCount(dataCount);
        paginationResult.setData(data);
        return paginationResult;
    }
}
