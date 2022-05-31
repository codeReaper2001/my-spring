package com.demo.springframework.beans.factory;

public interface BeanNameAware extends Aware {

    /*
    * 实现此接口，既能感知到所属的 BeanName
    * */
    void setBeanName(String name);
}
