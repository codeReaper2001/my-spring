package com.demo.springframework.context;

import com.demo.springframework.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {
    /**
     * 刷新容器
     * */
    void refresh() throws BeansException;

    void registerShutdownHook();

    void close();
}
