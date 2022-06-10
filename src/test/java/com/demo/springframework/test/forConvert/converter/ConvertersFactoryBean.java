package com.demo.springframework.test.forConvert.converter;

import com.demo.springframework.beans.factory.FactoryBean;
import com.demo.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ConvertersFactoryBean implements FactoryBean<Set<?>> {

    @Override
    public Set<?> getObject() throws Exception {
        Set<Converter<?, ?>> converters = new HashSet<>();
        Converter<String, LocalDate> stringToLocalDateConverter = new StringToLocalDateConverter("yyyy-MM-dd");
        converters.add(stringToLocalDateConverter);
        return converters;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
