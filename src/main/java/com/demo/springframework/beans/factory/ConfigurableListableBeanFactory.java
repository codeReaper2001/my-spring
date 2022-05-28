package com.demo.springframework.beans.factory;

import com.demo.springframework.beans.BeansException;
import com.demo.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.demo.springframework.beans.factory.config.BeanDefinition;
import com.demo.springframework.beans.factory.config.BeanPostProcessor;
import com.demo.springframework.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory,
        AutowireCapableBeanFactory,
        ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
