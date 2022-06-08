package com.demo.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.demo.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.demo.springframework.beans.factory.config.BeanDefinition;
import com.demo.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.demo.springframework.stereotype.Component;

import java.util.Set;

// 该类的主要作用是使用自身的扫描生成BeanDefinition集合的功能
// 然后将这些BeanDefinition注册到对应的BeanDefinitionRegistry（工厂）中去
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        // 遍历所有包路径
        for (String basePackage : basePackages) {
            // 当前包路径下得到的所有Component注解修饰的类的class对象（封装到了BeanDefinition中）
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                // 解析Bean作用域 singleton、prototype等
                // 可能得到singleton、prototype等，也可能为空字符串 ""
                String beanScope = resolveBeanScope(beanDefinition);
                // 如果scope不为空则设置
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                // 将BeanDefinition注册到工厂中
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
        // 注册处理注解的 BeanPostProcessor（@Autowired、@Value）
        registry.registerBeanDefinition("com.demo.springframework.context.annotation.internalAutowiredAnnotationProcessor",
                new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    // 获取bean的Scope，单例还是原型等
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        // 先取出class对象，反射查看Scope注解信息
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) return scope.value();
        return StrUtil.EMPTY;
    }

    // 决定beanName
    private String determineBeanName(BeanDefinition beanDefinition) {
        // 先得到class上修饰的Component注解上的内容，用来作为beanName
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String beanName = component.value();
        if (StrUtil.isEmpty(beanName)) {
            // 如果Component注解上没有命名，则取类名作为bean的名字
            beanName = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return beanName;
    }
}
