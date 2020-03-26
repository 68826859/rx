package com.rx.base.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface RxConfig {
	public static String NULL = "";
	public String key() default NULL;
    public String value() default NULL;
    public Class<?> valueType() default String.class;
    public String tag() default NULL;
}



