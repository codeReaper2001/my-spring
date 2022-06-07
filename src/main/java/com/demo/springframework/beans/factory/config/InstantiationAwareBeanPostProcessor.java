package com.demo.springframework.beans.factory.config;

import com.demo.springframework.beans.BeansException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * Apply this BeanPostProcessor <i>before the target bean gets instantiated</i>.
     * The returned bean object may be a proxy to use instead of the target bean,
     * effectively suppressing default instantiation of the target bean.
     *
     * 在 Bean 对象执行初始化方法之前，执行此方法
     *
     * @param beanClass bean类对象
     * @param beanName bean名
     * @return Object
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

}
