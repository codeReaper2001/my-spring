package com.demo.springframework.beans.factory;

import com.demo.springframework.beans.BeansException;

public interface BeanFactory {
    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object ...args) throws BeansException;
}
