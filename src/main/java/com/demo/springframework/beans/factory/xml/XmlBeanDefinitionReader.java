package com.demo.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import com.demo.springframework.beans.BeansException;
import com.demo.springframework.beans.PropertyValue;
import com.demo.springframework.beans.factory.config.BeanDefinition;
import com.demo.springframework.beans.factory.config.BeanReference;
import com.demo.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.demo.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.demo.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import com.demo.springframework.core.io.Resource;
import com.demo.springframework.core.io.ResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            try(InputStream inputStream = resource.getInputStream()) {
                doLoadBeanDefinitions(inputStream);
            }
        } catch (ClassNotFoundException | IOException | DocumentException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        // 改为使用dom4j读取xml文件的内容
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();

        // 解析 context:component-scan 标签，扫描包中的类并提取相关信息，用于组装 BeanDefinition
        Element componentScan = root.element("component-scan");
        // 存在component-scan 标签
        if (null != componentScan) {
            String scanPath = componentScan.attributeValue("base-package");
            if (StrUtil.isEmpty(scanPath)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            // 扫描类文件最终得到BeanDefinition，注入Bean工厂
            // 这里已经将用注解配置的Bean对应的BeanDefinition安置到工厂中了
            scanPackage(scanPath);
        }

        // 然后处理其他用xml配置的bean，转为BeanDefinition存起来
        List<Element> beanList = root.elements("bean");
        for (Element bean : beanList) {
            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethodName = bean.attributeValue("init-method");
            String destroyMethodName = bean.attributeValue("destroy-method");
            String beanScope = bean.attributeValue("scope");

            // 根据类名获取class
            Class<?> clazz = Class.forName(className);
            // 优先级 id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 定义Bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            // 获取xml上bean的属性列表
            List<Element> propertyList = bean.elements("property");
            // 读取属性并填充
            for (Element property : propertyList) {
                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attrRef = property.attributeValue("ref");
                // 如果是bean的引用则value为对应的BeanReference，否则为value值
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;

                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            // 如果工厂中已经存在了这个 k:beanName -> v: beanDefinition的beanName，则跑出异常
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            // 注册BeanDefinition到工厂
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    // 扫描对应包路径下的类文件，得到BeanDefinition等信息注册到工厂中
    private void scanPackage(String scanPath) {
        // 多个包路径，逗号分隔
        String[] basePackages = StrUtil.splitToArray(scanPath, ',');
        // 创建scanner，并把工厂注入进去，让scanner把扫描得到的BeanDefinition注册到工厂
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }
}
