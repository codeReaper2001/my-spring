package com.demo.springframework.beans.factory.support;

import com.demo.springframework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
