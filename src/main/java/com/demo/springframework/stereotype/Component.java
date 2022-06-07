package com.demo.springframework.stereotype;

import java.lang.annotation.*;

// 该注解放在类上，自动加载到IOC容器
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";

}
