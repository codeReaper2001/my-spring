package com.demo.springframework.beans.factory;

import com.demo.springframework.beans.BeansException;
import com.demo.springframework.beans.PropertyValue;
import com.demo.springframework.beans.PropertyValues;
import com.demo.springframework.beans.factory.config.BeanDefinition;
import com.demo.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.demo.springframework.core.io.DefaultResourceLoader;
import com.demo.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    /**
     * Default placeholder prefix: {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * Default placeholder suffix: {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 加载属性文件
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            // 遍历所有的BeanDefinition，内部遍历其中的String value，将${value}内容用location文件的内容替换
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)) continue;
                    String strVal = (String) value;
                    StringBuilder buffer = new StringBuilder(strVal);
                    // 查找字符串中是否含有${value}
                    int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
                        // ${value}中的value部分
                        String propKey = strVal.substring(startIdx + 2, stopIdx);
                        // 获取替换的值
                        String propVal = properties.getProperty(propKey);
                        // 替换原字符串的值
                        buffer.replace(startIdx, stopIdx + 1, propVal);
                        // 设置回BeanDefinition中
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), buffer.toString()));
                    }
                }
            }
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
