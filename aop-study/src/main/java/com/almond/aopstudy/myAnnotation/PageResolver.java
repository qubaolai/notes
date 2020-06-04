package com.almond.aopstudy.myAnnotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PageResolver {

    public String countPerPage() default "0";

    public String currentPageNumber() default "1";
}
