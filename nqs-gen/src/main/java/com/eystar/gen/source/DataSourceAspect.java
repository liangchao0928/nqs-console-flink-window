package com.eystar.gen.source;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class DataSourceAspect {
    @Before("@annotation(ds)")
    public void beforeDataSource(DataSource ds) {
        DataSourceType value = ds.value();
        DataSourceContextHolder.setDataSource(value);
    }

    @After("@annotation(ds)")
    public void afterDataSOurce(DataSource ds) {
        DataSourceContextHolder.clearDataSource();
    }
}