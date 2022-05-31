package com.demo.springframework.beans.factory;

public interface BeanClassLoaderAware extends Aware {

    /*
    * 实现此接口，既能感知到所属的 ClassLoader
    * */
    void setBeanClassLoader(ClassLoader classLoader);
}
