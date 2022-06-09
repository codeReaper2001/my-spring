package com.demo.springframework.beans.factory;

import com.demo.springframework.beans.BeansException;

public interface ObjectFactory<T> {

    T getObject() throws BeansException;

}
