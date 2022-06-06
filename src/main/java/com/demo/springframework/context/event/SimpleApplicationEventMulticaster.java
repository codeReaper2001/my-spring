package com.demo.springframework.context.event;

import com.demo.springframework.beans.factory.BeanFactory;
import com.demo.springframework.context.ApplicationEvent;
import com.demo.springframework.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<ApplicationEvent> listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
