package com.demo.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.demo.springframework.beans.factory.config.BeanDefinition;
import com.demo.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        // 使用hutool工具类扫描某包下的所有有Component注解修饰的所有class文件，
        // 加载到内存中，然后返回class对象集合
        // 再遍历这些class对象，为每个class对象生成对应的BeanDefinition放入到集合中（还没有设置属性填充）
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }

}
