package com.demo.springframework.aop.framework.autoproxy;

import com.demo.springframework.aop.*;
import com.demo.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.demo.springframework.aop.framework.ProxyFactory;
import com.demo.springframework.beans.BeansException;
import com.demo.springframework.beans.PropertyValues;
import com.demo.springframework.beans.factory.BeanFactory;
import com.demo.springframework.beans.factory.BeanFactoryAware;
import com.demo.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.demo.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

public class DefaultAdvisorAutoProxyCreator
        implements InstantiationAwareBeanPostProcessor,
        BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    // 在初始化后置处理中实现<<bean>>到<<bean的代理对象>>的转变
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 获取class对象
        Class<?> beanClass = bean.getClass();
        if (isInfrastructureClass(beanClass)) return bean;

        // 获取切入点访问者集合
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        // 遍历访问者，这里取第一个
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            // 获取访问者的ClassFilter，用于查看当前bean的类是否满足切面表达式的要求
            // 即是否存在需要加强的方法
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 当前访问者不能增强当前bean，continue
            if (!classFilter.matches(beanClass)) continue;

            // 可以对当前bean进行增强
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            // 即使用当前bean封装为方法增强后的bean代理对象进行返回
            return new ProxyFactory(advisedSupport).getProxy();
        }

        // 没有符合的访问者，则直接返回原bean而不是bean代理对象
        return bean;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return null;
    }
}
