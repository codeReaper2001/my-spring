package com.demo.springframework.aop;

import com.demo.springframework.util.ClassUtils;

public class TargetSource {

    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * Return the type of targets returned by this {@link TargetSource}.
     * <p>Can return <code>null</code>, although certain usages of a
     * <code>TargetSource</code> might just work with a predetermined
     * target class.
     * @return the type of targets returned by this {@link TargetSource}
     */
    public Class<?>[] getTargetClass() {
        // 先得到代理对象对应的class对象
        Class<?> clazz = this.target.getClass();
        /*
        * 如果代理对象的class对象是使用Cglib生成的，则其父类class对象
        * 才是代理对象的class对象
        * */
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        return clazz.getInterfaces();
    }

    /**
     * Return a target instance. Invoked immediately before the
     * AOP framework calls the "target" of an AOP method invocation.
     * @return the target object, which contains the joinpoint
     * @throws Exception if the target object can't be resolved
     */
    public Object getTarget() {
        return this.target;
    }
}
