package com.demo.springframework.test.common;

import com.demo.springframework.beans.BeansException;
import com.demo.springframework.beans.PropertyValue;
import com.demo.springframework.beans.PropertyValues;
import com.demo.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.demo.springframework.beans.factory.config.BeanDefinition;
import com.demo.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
    }
}
