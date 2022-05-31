package com.demo.springframework.context;

import com.demo.springframework.beans.BeansException;
import com.demo.springframework.beans.factory.Aware;
import com.demo.springframework.context.ApplicationContext;

public interface ApplicationContextAware extends Aware {

    /*
    * 实现此接口，既能感知到所属的 ApplicationContext
    * */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
