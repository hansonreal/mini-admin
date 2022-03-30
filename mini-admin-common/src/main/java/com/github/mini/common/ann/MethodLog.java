package com.github.mini.common.ann;

import java.lang.annotation.*;

/*
 * 方法级日志切面注解
 * @author hanson
 * @date 2022/03/08 19:00
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {
    String remark() default "";
}
