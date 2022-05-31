package com.demo.springframework.beans.factory;

import com.demo.springframework.beans.BeansException;

public interface BeanFactoryAware extends Aware {

    /*
    * 实现此接口，既能感知到所属的 BeanFactory
    * */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
