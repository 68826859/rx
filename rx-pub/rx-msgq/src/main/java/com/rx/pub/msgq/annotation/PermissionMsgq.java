package com.rx.pub.msgq.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.rx.pub.msgq.enm.PermissionEnumMsgq;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionMsgq {
	PermissionEnumMsgq[] value() default {};
}
