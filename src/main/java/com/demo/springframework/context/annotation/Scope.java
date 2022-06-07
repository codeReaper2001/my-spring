package com.demo.springframework.context.annotation;

import java.lang.annotation.*;

// 可以放在类和方法上
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default "singleton";

}
