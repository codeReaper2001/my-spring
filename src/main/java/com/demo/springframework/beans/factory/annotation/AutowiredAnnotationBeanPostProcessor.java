package com.demo.springframework.beans.factory.annotation;

import com.demo.springframework.beans.BeansException;
import com.demo.springframework.beans.PropertyValue;
import com.demo.springframework.beans.PropertyValues;
import com.demo.springframework.beans.factory.BeanFactory;
import com.demo.springframework.beans.factory.BeanFactoryAware;
import com.demo.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.demo.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.demo.springframework.util.ClassUtils;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor
        implements InstantiationAwareBeanPostProcessor,
        BeanFactoryAware {

    private ConfigurableBeanFactory beanFactory;

    // 注入BeanFactory
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        PropertyValues addPvs = new PropertyValues();

        // 1.处理注解 @Value
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        // 获取bean类中的所有字段
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field : declaredFields) {
            // 取出字段上的Value注解
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (null != valueAnnotation) {
                String strVal = valueAnnotation.value();
                // 将Value注解中的内容先进行解析
                strVal = beanFactory.resolveEmbeddedValue(strVal);
                // 修改BeanDefinition中的pvs，待后续注入到bean中
                addPvs.addPropertyValue(new PropertyValue(field.getName(), strVal));
            }
        }

        // 2. 处理注解 @Autowired
        for (Field field : declaredFields) {
            // 取出字段上的Autowired注解
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (null != autowiredAnnotation) {
                // 字段使用@Autowired修饰
                Class<?> fieldType = field.getType();
                // 获取段上的Qualifier注解
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                // 需要注入的对象
                Object dependentBean = null;
                if (null != qualifierAnnotation) {
                    // 字段使用@Qualifier修饰，此时使用Qualifier的值来作为依赖的bean
                    String dependentBeanName = qualifierAnnotation.value();
                    // 根据依赖的bean的beanName获取bean
                    dependentBean = beanFactory.getBean(dependentBeanName);
                } else {
                    // 否则根据属性类型获取bean
                    dependentBean = beanFactory.getBean(fieldType);
                }
                // 修改BeanDefinition中的pvs，待后续注入到bean中
                addPvs.addPropertyValue(new PropertyValue(field.getName(), dependentBean));
            }
        }

        return addPvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
