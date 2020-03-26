package com.rx.pub.role.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.rx.pub.role.enm.RolePermissionEumn;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface PermissionRole {
	RolePermissionEumn[] value() default {};
}
