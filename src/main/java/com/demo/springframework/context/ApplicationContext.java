package com.demo.springframework.context;

import com.demo.springframework.beans.factory.HierarchicalBeanFactory;
import com.demo.springframework.beans.factory.ListableBeanFactory;
import com.demo.springframework.core.io.ResourceLoader;

public interface ApplicationContext
        extends ListableBeanFactory, HierarchicalBeanFactory,
        ResourceLoader, ApplicationEventPublisher {
}
