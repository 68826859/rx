package com.rx.pub.crud.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.rx.pub.crud.enm.PermissionEumn;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface PermissionCrud {
	PermissionEumn[] value() default {};
}
