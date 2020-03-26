package com.rx.pub.dic.annotation;

import com.rx.pub.dic.enm.DicPermissionEnum;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionDic {
	DicPermissionEnum[] value() default {};
}
